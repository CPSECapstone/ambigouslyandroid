package edu.calpoly.flipted.backend

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.EditOrCreateGoalMutation
import edu.calpoly.flipted.GetAllGoalsQuery
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import edu.calpoly.flipted.type.GoalInput
import edu.calpoly.flipted.type.SubGoalInput

class ApolloGoalsRepo(authProvider: AuthProvider) : ApolloRepo(authProvider), GoalsRepo {

    override suspend fun getAllGoals(): List<Goal> {
        val response = try {
            apolloClient().query(GetAllGoalsQuery()).await()
        } catch(e: ApolloException) {
            //Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            //Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val goals = response.data?.getAllGoals ?: throw IllegalStateException("Error when querying backend: bad response")
        return goals.map{
            it.fragments.allGoalFields
        }.map{
            goal -> Goal(goal.id, goal.title, goal.dueDate, goal.completed, goal.completedDate,
                goal.subGoals.map {
                    subGoal -> SubGoal(subGoal.title, subGoal.dueDate, subGoal.completed, subGoal.completedDate)
                }, goal.category, goal.favorited, goal.assignee == goal.owner, goal.pointValue)
        }

    }

    override suspend fun getGoalById(id: String): Goal {
        return getAllGoals().find { it.uid == id } ?: throw IllegalArgumentException("Could not find goal with id $id")
    }

    override suspend fun editGoal(goal: Goal): Goal {
        val goalInput = GoalInput(Input.optional(goal.uid), goal.title, goal.dueDate, goal.completed, Input.optional(goal.completedDate),
                goal.subGoals.map {
                    SubGoalInput(it.title, it.dueDate, it.completed, Input.optional(it.completedDate))
                }, goal.category, goal.favorited, Input.absent(), Input.absent(), Input.absent())
        val mutation = EditOrCreateGoalMutation(goalInput)
        val response = try {
            apolloClient().mutate(mutation).await()
        } catch(e: ApolloException) {
            //Log.e("ApolloTasksRepo", "Error when querying backend", e)
            throw e
        }

        if(response.hasErrors() || response.data == null) {
            //Log.e("ApolloTasksRepo", "Error when querying backend: ${response.errors?.map {it.message} ?: "bad response"}")
            throw IllegalStateException("Error when querying backend: bad response")
        }

        val id = response.data?.editOrCreateGoal ?: throw IllegalStateException("Error when querying backend: bad response")

        return Goal(goal.title, id, goal.dueDate, goal.completed, goal.completedDate, goal.subGoals,
                goal.category, goal.favorited, goal.isOwnedByStudent, goal.pointValue)
    }
}