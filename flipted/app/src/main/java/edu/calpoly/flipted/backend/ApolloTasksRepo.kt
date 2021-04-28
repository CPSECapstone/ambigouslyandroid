package edu.calpoly.flipted.backend

import android.util.Log
import edu.calpoly.flipted.type.TaskProgressInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.*
import edu.calpoly.flipted.businesslogic.quizzes.AnswerResult
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.FreeResponseQuestion
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceAnswerOption
import edu.calpoly.flipted.businesslogic.quizzes.data.questions.MultipleChoiceQuestion
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.*
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.ImageBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.TextBlock
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.VideoBlock
import edu.calpoly.flipted.type.FreeResponseAnswerInput
import edu.calpoly.flipted.type.MultipleChoiceAnswerInput

class ApolloTasksRepo : ApolloRepo(), TasksRepo {

    override suspend fun getTask(taskId: String): Task {
        val response = try {
            apolloClient().query(GetTaskQuery(taskId)).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val task = response.data?.task ?: throw badResponseException

        val completedRequirementIds : Set<String> = response.data?.retrieveTaskProgress?.finishedRequirementIds?.toSet() ?: setOf()
        val completedQuestions : Map<String, GetTaskQuery.Answer> = response.data?.retrieveQuestionProgress?.answers?.map {
            (it.questionId ?: throw badResponseException) to it
        }?.toMap() ?: mapOf()

        return Task(task.pages.map { page ->
            Page(page.blocks?.map { block ->
                if(block == null) throw badResponseException
                when {
                    block.asTextBlock != null ->
                        TextBlock(block.asTextBlock.contents, block.asTextBlock.fontSize, block.title)
                    block.asImageBlock != null ->
                        ImageBlock(block.asImageBlock.imageUrl, block.title)
                    block.asVideoBlock != null ->
                        VideoBlock(block.asVideoBlock.videoUrl, block.title)
                    block.asQuizBlock != null ->
                        QuizBlock(block.asQuizBlock.questions?.map { question ->
                            if(question == null) throw badResponseException
                            when {
                                question.asMCQuestion != null -> {
                                    val answerOptions = question.asMCQuestion.options?.map { answerOption ->
                                        if(answerOption == null) throw badResponseException
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
        task.parentMissionId,
        task.parentMissionIndex,
        task.objectiveId)
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
        } catch(e: ApolloException) {
            e.printStackTrace()
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            return
        }
    }

    override suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
        val mutation = when(answer.answer.chosenAnswerValue) {
            is MultipleChoiceAnswer -> SaveMcProgressMutation(MultipleChoiceAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, answer.answer.chosenAnswerValue.chosenAnswer.id))
            is FreeResponseAnswer -> SaveFreeResponseProgressMutation(FreeResponseAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, answer.answer.chosenAnswerValue.response))
            else -> throw IllegalArgumentException("Unknown answer type")
        }

        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            return
        }
    }

    override suspend fun submitTask(taskId: String): TaskSubmissionResult {
        val mutation = SubmitTaskMutation(taskId)
        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            val message = response.errors?.get(0)?.message
            if (message!!.contains("rubric")) {
                return TaskSubmissionResult(taskId, false, 0, 0, emptyList(), message)
            }
            else {
                Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
                throw IllegalStateException("Error when querying backend: bad response")
            }
        }
        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val result = response.data?.submitTask?: throw badResponseException

        return TaskSubmissionResult(taskId, result.graded, result.pointsAwarded!!, result.pointsPossible!!,
                result.questionAndAnswers!!.map { qa ->
                    AnswerResult(when {
                        qa.question.asMCQuestion != null ->
                            qa.question.asMCQuestion.id
                        else ->
                           qa.question.asFRQuestion?.id!!
                    } ,
                    when {
                        qa.question.asMCQuestion != null ->
                            qa.question.asMCQuestion.answers!!.map{it.toString()}
                        else ->
                            listOf(qa.question.asFRQuestion?.answer!!)
                    }
                    , qa.answer?.answer!!, qa.answer.pointsAwarded!!)
                }, "")
    }

}