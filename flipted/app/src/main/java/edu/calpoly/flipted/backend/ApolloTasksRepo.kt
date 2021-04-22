package edu.calpoly.flipted.backend

import android.util.Log
import edu.calpoly.flipted.type.TaskProgressInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.SubmitTaskProgressMutation
import edu.calpoly.flipted.businesslogic.tasks.TasksRepo
import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskQuizAnswer
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

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

    override suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
        TODO("Not yet implemented")
    }

    override suspend fun submitTask(taskId: String): TaskSubmissionResult {
        TODO("Not yet implemented")
    }

}