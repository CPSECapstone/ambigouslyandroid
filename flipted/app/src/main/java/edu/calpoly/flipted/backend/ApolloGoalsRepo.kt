package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.EditOrCreateGoalMutation
import edu.calpoly.flipted.GetAllGoalsQuery
import edu.calpoly.flipted.SubmitTaskMutation
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.businesslogic.goals.UnsavedNewGoal
import edu.calpoly.flipted.type.GoalInput
import edu.calpoly.flipted.type.SubGoalInput
import java.text.SimpleDateFormat
import java.util.*

class ApolloGoalsRepo : ApolloRepo(), GoalsRepo {

    override suspend fun getAllGoals(): List<Goal> {
        Log.e("tag", "get goals")
        val response = try {
            apolloClient().query(GetAllGoalsQuery()).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val goals = response.data?.getAllGoals?.toMutableList() ?: throw IllegalStateException("Error when querying backend: bad response")

        val results = mutableListOf<Goal>()

        goals.forEach {
            results.add(Goal(it.title, it.id, it.dueDate, it.completedDate,
                    it.subGoals.map { sg ->
                        SubGoal(sg.title, "0",
                                sg.dueDate,
                                sg.completed,
                                sg.completedDate)
                    },
                    it.completed, it.category, it.favorited, false, it.pointValue))
        }

        return results

    }

    override suspend fun getGoalById(id: String): Goal {
        val response = try {
            apolloClient().query(GetAllGoalsQuery()).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val goals = response.data?.getAllGoals?.toMutableList() ?: throw IllegalStateException("Error when querying backend: bad response")

        val result = goals.find{it.id==id}!!


        return Goal(result.title, result.id, result.dueDate, result.completedDate,
                result.subGoals.map { sg ->
                    SubGoal(sg.title, "0",
                            sg.dueDate,
                            sg.completed,
                            sg.completedDate)
                },
                result.completed, result.category, result.favorited, false, result.pointValue)
    }

    override suspend fun saveNewGoal(goal: UnsavedNewGoal): Goal {
        val goalInput = GoalInput(Input.absent(), goal.title, goal.dueDate, false, Input.absent(),
                goal.subGoals.map {
                    SubGoalInput(it.title, it.dueDate, false, Input.absent())
                }, goal.category, goal.favorited, Input.absent(), Input.absent(), Input.absent())
        val mutation = EditOrCreateGoalMutation(goalInput)
        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val id = response.data?.editOrCreateGoal ?: throw IllegalStateException("Error when querying backend: bad response")

        return Goal(goal.title, id, goal.dueDate, null, listOf(), false,
            goal.category, goal.favorited, goal.ownedByStudent, goal.pointValue)
    }

    override suspend fun editGoal(goal: Goal): Goal {
        val goalInput = GoalInput(Input.optional(goal.uid), goal.title, goal.dueDate, false, Input.absent(),
                goal.subGoals.map {
                    SubGoalInput(it.title, it.dueDate, it.completed, Input.optional(it.completedDate))
                }, goal.category, goal.favorited, Input.absent(), Input.absent(), Input.absent())
        val mutation = EditOrCreateGoalMutation(goalInput)
        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        return goal
    }
}