package edu.calpoly.flipted.backend

import android.util.Log
import edu.calpoly.flipted.type.TaskProgressInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.GetTasksQuery
import edu.calpoly.flipted.SaveFreeResponseProgressMutation
import edu.calpoly.flipted.SaveMcProgressMutation
import edu.calpoly.flipted.SubmitTaskProgressMutation
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
            apolloClient().query(GetTasksQuery(taskId)).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: bad response")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val badResponseException = IllegalStateException("Error when querying backend: bad response")

        val task = response.data?.task ?: throw badResponseException

        // TODO: Fill in the completedRequirementIds using the separate query
        val completedRequirementIds : Set<String> = setOf()

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
                                question.asMCQuestion != null ->
                                    MultipleChoiceQuestion(question.asMCQuestion.options?.map { answerOption ->
                                        if(answerOption == null) throw badResponseException
                                        MultipleChoiceAnswerOption(answerOption.description, answerOption.id)
                                    } ?: throw badResponseException,
                                    question.description,
                                    question.points,
                                    question.id)
                                else ->
                                    FreeResponseQuestion(question.description, question.points, question.id)
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
        task.subMissionId,
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
            Log.e("ApolloTasksRepo", "Error when querying backend: bad response")
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
            Log.e("ApolloTasksRepo", "Error when querying backend: bad response")
            return
        }
    }

    override suspend fun submitTask(taskId: String): TaskSubmissionResult {
        TODO("Not yet implemented")
    }

}