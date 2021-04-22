package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.api.Input
import edu.calpoly.flipted.type.TaskProgressInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.SubmitTaskProgressMutation
import java.util.*
import edu.calpoly.flipted.businesslogic.tasks.TasksTempRepo
import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress

class ApolloTasksRepo : ApolloRepo(), TasksTempRepo {
    override suspend fun submitTaskProgress(taskProgress: TaskRubricProgress): String? {
        val ProgressInput : Input<TaskProgressInput> = Input.optional(TaskProgressInput(taskId = taskProgress.taskId,
                finishedRequirementIds = taskProgress.finishedRequirements))
                val response = try {
            apolloClient().mutate((SubmitTaskProgressMutation(ProgressInput))).await()
        } catch(e: ApolloException) {
            e.printStackTrace()
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            return null
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: bad response")
            return null
        }

        return try {
            response.data!!.submitTaskProgress

        } catch(e : NullPointerException) {
            Log.e("ApolloTasksRepo", "Failed null check when processing received data")
            null
        }
    }

}