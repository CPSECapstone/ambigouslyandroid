package edu.calpoly.flipted.backend

import android.util.Log
import edu.calpoly.flipted.type.TaskProgressInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.SaveFreeResponseProgressMutation
import edu.calpoly.flipted.SaveMcProgressMutation
import edu.calpoly.flipted.SubmitTaskProgressMutation
import edu.calpoly.flipted.businesslogic.errors.BackendError
import edu.calpoly.flipted.businesslogic.errors.ResponseOrError
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.QuizBlockStudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult
import edu.calpoly.flipted.type.FreeResponseAnswerInput
import edu.calpoly.flipted.type.MultipleChoiceAnswerInput

class ApolloTasksRepo : ApolloRepo(), TasksRepo {
    override suspend fun getTask(taskId: String): Task {
        TODO("Not yet implemented")
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

    override suspend fun saveQuizAnswer(answer: QuizBlockStudentAnswerInput): ResponseOrError<QuizBlockStudentAnswerInput> {
        val mutation = when(answer.answer.chosenAnswerValue) {
            is MultipleChoiceAnswer -> SaveMcProgressMutation(MultipleChoiceAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, answer.answer.chosenAnswerValue.chosenAnswer.id))
            is FreeResponseAnswer -> SaveFreeResponseProgressMutation(FreeResponseAnswerInput(answer.task.uid, answer.block.uid, answer.answer.questionId, answer.answer.chosenAnswerValue.response))
            else -> throw IllegalArgumentException("Unknown answer type")
        }

        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return ResponseOrError.withError(BackendError("Error when querying backend: ${e.localizedMessage}"))
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: bad response")
            return ResponseOrError.withError(BackendError("Error when querying backend: ${response.errors?.map{it.message}}"))
        }

        return ResponseOrError.withResponse(answer)
    }

    override suspend fun submitTask(taskId: String): TaskSubmissionResult {
        TODO("Not yet implemented")
    }

}