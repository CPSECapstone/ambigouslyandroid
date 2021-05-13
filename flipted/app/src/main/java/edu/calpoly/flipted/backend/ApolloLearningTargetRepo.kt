package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.GetAllTargetProgressQuery
import edu.calpoly.flipted.businesslogic.learningTargets.*

class ApolloLearningTargetRepo: ApolloRepo(), LearningTargetRepo {

    override suspend fun getAllTargetProgress(courseId: String, studentId: String?): List<TargetProgress> {
        val response = try {
            apolloClient().query(GetAllTargetProgressQuery(courseId)).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if (response.hasErrors() || response.data == null) {
            Log.e(
                "ApolloTasksRepo",
                "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val target = response.data?.getAllTargetProgress
            ?: throw IllegalStateException("Error when querying backend: bad response")
        return target.map { targetProgress ->
            TargetProgress(targetProgress.target.let { target ->
                LearningTarget(target.targetName) },targetProgress.objectives.map { objective ->
                    ObjectiveProgress(objective.objectiveId,objective.objectiveName,objective.tasks.map {
                        TaskObjectiveProgress(it.taskId,it.taskName,it.mastery)
                    })
            })
        }
    }

}