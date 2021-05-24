package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.GetAllMissionProgressQuery
import edu.calpoly.flipted.businesslogic.missions.*
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

class ApolloMissionsRepo : ApolloRepo(), MissionsRepo {

    override suspend fun getAllMissionProgress(courseId: String): List<MissionProgress> {
        val response = try {
            apolloClient().query(GetAllMissionProgressQuery(courseId)).await()
        } catch (e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if (response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map { it.message } ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val missions = response.data?.getAllMissionProgress ?: throw IllegalStateException("Error when querying backend: bad response")


        return missions.map { missionProgress ->
            val tasks = missionProgress.mission.let{ mission ->
                mission.missionContent?.mapNotNull { content ->
                    content?.asTask?.let { task ->
                        SparseTask(task.id, task.name, task.instructions, task.points, task.dueDate)
                    }
                }?.associateBy {
                    it.id
                } ?: throw IllegalStateException("Error when querying backend: bad response")
            }
            MissionProgress(
                    missionProgress.mission.let{ mission ->
                        Mission(mission.id, mission.name, mission.description,
                            tasks.values.toList()
                        )
                    },
                    missionProgress.progress.map{ taskStat ->
                        TaskStats(
                                tasks[taskStat.taskId] ?: throw IllegalArgumentException("Error when querying backend: bad response"),
                                taskStat.submission?.let{ submission ->
                                    TaskSubmissionResult(
                                            taskStat.taskId,
                                            submission.graded,
                                            submission.pointsAwarded ?: throw IllegalArgumentException("Error when querying backend: bad response"),
                                            submission.pointsPossible ?: throw IllegalArgumentException("Error when querying backend: bad response"),
                                            listOf()
                                    )
                                }
                        )
                    }
            )
        }
    }



}