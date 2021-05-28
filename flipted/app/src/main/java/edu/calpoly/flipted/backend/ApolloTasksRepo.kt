package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.*
import edu.calpoly.flipted.businesslogic.quizzes.AnswerResult
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceAnswerOption
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.VideoBlock

import edu.calpoly.flipted.businesslogic.targets.Mastery
import edu.calpoly.flipted.type.FreeResponseAnswerInput
import edu.calpoly.flipted.type.MultipleChoiceAnswerInput
import edu.calpoly.flipted.type.TaskProgressInput
import edu.calpoly.flipted.type.Mastery as ApolloMastery

class ApolloTasksRepo : ApolloRepo(), TasksRepo {

    override suspend fun getTaskInfo(taskId: String): Task {
        val response = try {
            apolloClient().query(GetTaskQuery(taskId)).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if (response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val task = response.data?.task ?: throw badResponseException

        return Task(listOf(), listOf(),
            task.id,
            task.name,
            task.instructions,
            task.points,
            task.startAt,
            task.endAt,
            task.dueDate,
            task.missionId,
            task.missionIndex,
            "")
    }

    override suspend fun getTask(taskId: String): Task {
        val response = try {
            apolloClient().query(GetTaskQuery(taskId)).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if (response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val task = response.data?.task ?: throw badResponseException

        val completedRequirementIds: Set<String> = response.data?.retrieveTaskProgress?.finishedRequirementIds?.toSet()
                ?: setOf()
        val completedQuestions: Map<String, GetTaskQuery.Answer> = response.data?.retrieveQuestionProgress?.answers?.map {
            (it.questionId ?: throw badResponseException) to it
        }?.toMap() ?: mapOf()

        return Task(task.pages.map { page ->
            Page(page.blocks?.map { block ->
                if (block == null) throw badResponseException
                when {
                    block.asTextBlock != null ->
                        TextBlock(block.asTextBlock.contents
                                ?: throw badResponseException, block.asTextBlock.fontSize
                                ?: throw badResponseException, block.title)
                    block.asImageBlock != null ->
                        ImageBlock(block.asImageBlock.imageUrl, block.title)
                    block.asVideoBlock != null ->
                        VideoBlock(block.asVideoBlock.videoUrl, block.title)
                    block.asQuizBlock != null ->
                        QuizBlock(block.asQuizBlock.questions?.map { question ->
                            if (question == null) throw badResponseException
                            when {
                                question.asMcQuestion != null -> {
                                    val answerOptions = question.asMcQuestion.options?.map { answerOption ->
                                        if (answerOption == null) throw badResponseException
                                        MultipleChoiceAnswerOption(answerOption.description, answerOption.id)
                                    } ?: throw badResponseException

                                    MultipleChoiceQuestion(
                                            answerOptions,
                                            question.description,
                                            question.points,
                                            question.id,
                                            answerOptions.find {
                                                it.id.toString() == completedQuestions[question.id]?.answer
                                            }?.let {
                                                MultipleChoiceAnswer(it)
                                            }
                                    )
                                }
                                else ->
                                    FreeResponseQuestion(question.description, question.points, question.id,
                                            completedQuestions[question.id]?.answer?.let {
                                                FreeResponseAnswer(it)
                                            }
                                    )
                            }
                        } ?: throw badResponseException,
                                block.asQuizBlock.requiredScore ?: throw badResponseException,
                                block.asQuizBlock.blockId ?: throw badResponseException,
                                block.asQuizBlock.points ?: throw badResponseException,
                                block.title)
                    else -> throw badResponseException
                }
            } ?: throw badResponseException, page.skippable ?: throw badResponseException)
        },
                task.requirements.map { requirement ->
                    RubricRequirement(requirement.description ?: throw badResponseException,
                            completedRequirementIds.contains(requirement.id),
                            requirement.id)
                },
                task.id,
                task.name,
                task.instructions,
                task.points,
                task.startAt,
                task.endAt,
                task.dueDate,
                task.missionId,
                task.missionIndex,
                "temp")
    }

    override suspend fun saveRubricProgress(progress: TaskRubricProgress) {
        val progressInput = TaskProgressInput(
                progress.task.uid,
                progress.finishedRequirements.map {
                    it.uid
                })
        val progressMutation = SubmitTaskProgressMutation(progressInput)
        val response = try {
            apolloClient().mutate(progressMutation).await()
        } catch (e: ApolloException) {
            e.printStackTrace()
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return
        }

        if (response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            return
        }
    }

    override suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
        val mutation = when (val chosenAnswerValue = answer.answer.chosenAnswerValue) {
            is MultipleChoiceAnswer -> SaveMcProgressMutation(MultipleChoiceAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, chosenAnswerValue.chosenAnswer.id))
            is FreeResponseAnswer -> SaveFreeResponseProgressMutation(FreeResponseAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, chosenAnswerValue.response))
            else -> throw IllegalArgumentException("Unknown answer type")
        }

        val response = try {
            apolloClient().mutate(mutation).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return
        }

        if (response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            return
        }
    }

    override suspend fun submitTask(taskId: String): TaskSubmissionResult {
        val mutation = SubmitTaskMutation(taskId)
        val response = try {
            apolloClient().mutate(mutation).await()
        } catch (e: ApolloException) {
            val message = "Task submission unavailable right now. Please make sure you are not offline."
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw IllegalStateException(message)
        }

        if (response.hasErrors() || response.data == null) {
            val message = response.errors?.get(0)?.message
            if (message != null) {
                throw IllegalStateException(message)
            } else {
                Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
                throw IllegalStateException("Task submission unavailable right now.")
            }
        }
        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val result = response.data?.submitTask ?: throw badResponseException

        return TaskSubmissionResult(taskId, result.graded, result.pointsAwarded!!, result.pointsPossible!!, result.teacherComment,
                result.questionAndAnswers!!.map { qa ->
                    AnswerResult(when {
                        qa.question.asMcQuestion != null ->
                            qa.question.asMcQuestion.id
                        else ->
                            qa.question.asFrQuestion?.id!!
                    },
                            when {
                                qa.question.asMcQuestion != null ->
                                    qa.question.asMcQuestion.answers!!.map { it.toString() }
                                qa.question.asFrQuestion?.answer != null ->
                                    listOf(qa.question.asFrQuestion.answer)
                                else ->
                                    listOf("")
                            }, qa.answer?.answer!!, qa.answer.pointsAwarded!!, qa.answer.teacherComment)
                })
    }

    override suspend fun retrieveTaskSubmission(taskId: String): TaskSubmissionResult {
        val query = RetrieveTaskSubmissionQuery(taskId)
        val response = try {
            apolloClient().query(query).await()
        } catch (e: ApolloException) {
            val message = "Task submission unavailable right now. Please make sure you are not offline."
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw IllegalStateException(message)
        }

        if (response.hasErrors() || response.data == null) {
            val message = response.errors?.get(0)?.message
            if (message != null) {
                throw IllegalStateException(message)
            } else {
                Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
                throw IllegalStateException("Task submission unavailable right now.")
            }
        }
        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val result = response.data?.retrieveTaskSubmission ?: throw badResponseException

        return TaskSubmissionResult(taskId, result.graded, result.pointsAwarded!!, result.pointsPossible!!, result.teacherComment,
            result.questionAndAnswers!!.map { qa ->
                AnswerResult(when {
                    qa.question.asMcQuestion != null ->
                        qa.question.asMcQuestion.id
                    else ->
                        qa.question.asFrQuestion?.id!!
                },
                    when {
                        qa.question.asMcQuestion != null ->
                            qa.question.asMcQuestion.answers!!.map { it.toString() }
                        qa.question.asFrQuestion?.answer != null ->
                            listOf(qa.question.asFrQuestion.answer)
                        else ->
                            listOf("")
                    }, qa.answer?.answer!!, qa.answer.pointsAwarded!!, qa.answer.teacherComment)
            })
    }

    override suspend fun getObjectiveProgress(taskId: String): List<TaskObjectiveProgress> {
        val response = try {
            apolloClient().query(GetTaskObjectiveProgressQuery(taskId)).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        val data = response.data

        if (response.hasErrors() || data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        return data.getTaskObjectiveProgress.map { taskObjProg ->
            TaskObjectiveProgress(
                taskObjProg.task.id, taskObjProg.task.name,
                taskObjProg.objective.objectiveId, taskObjProg.objective.objectiveName,
                when(taskObjProg.mastery) {
                    ApolloMastery.NOT_GRADED -> Mastery.NOT_GRADED
                    ApolloMastery.NOT_MASTERED -> Mastery.NOT_MASTERED
                    ApolloMastery.NEARLY_MASTERED -> Mastery.NEARLY_MASTERED
                    ApolloMastery.MASTERED -> Mastery.MASTERED
                    else -> throw IllegalArgumentException("Error when querying backend: bad response")
                }
            )
        }
    }

}