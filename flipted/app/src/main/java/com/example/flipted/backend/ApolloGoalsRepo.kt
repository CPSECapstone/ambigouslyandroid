package com.example.flipted.backend

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import edu.calpoly.flipted.AllGoalsQuery
import edu.calpoly.flipted.SaveCompletionMutation
import com.example.flipted.businesslogic.goals.Goal
import com.example.flipted.businesslogic.goals.GoalCompletion
import com.example.flipted.businesslogic.goals.GoalsRepo
import java.util.*

class ApolloGoalsRepo : GoalsRepo {

    private val apolloClient = ApolloClient.builder()
        .serverUrl("https://f6t0mvy5y0.execute-api.us-east-1.amazonaws.com/dev/graphql")
        .build()

    override suspend fun getAllGoals(): List<Goal> {
        val response = try {
            apolloClient.query(AllGoalsQuery()).await()
        } catch(e: ApolloException) {
            Log.e("ApolloGoalsRepo", "Error when querying backend", e)
            return listOf()
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloGoalsRepo", "Error when querying backend: bad response")
            return listOf()
        }

        return try {
            response.data!!.goals!!.map {
                Goal(it!!.title, it.id!!, Date(it.dueDate.toLong()), it.target!!, it.unit,
                    it.completions!!.map { comp ->
                        GoalCompletion(
                            comp!!.name,
                            it.id,
                            Date(comp.date.toLong())
                        )
                    }
                        .toMutableList())
            }
        } catch(e : NullPointerException) {
            Log.e("ApolloGoalsRepo", "Failed null check when processing received data")
            listOf()
        }
    }

    override suspend fun getGoalById(id: Int): Goal? {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewCompletion(completion: GoalCompletion): Goal? {
        val response = try {
            apolloClient.mutate(SaveCompletionMutation(completion.parentId, completion.description, completion.completedDate.time.toString())).await()
        } catch(e: ApolloException) {
            Log.e("ApolloGoalsRepo", "Error when mutating backend: ${e.message}")
            return null
        }

        if(response.hasErrors() || response.data == null) {
            Log.e("ApolloGoalsRepo", "Error when mutating backend: bad response")
            return null
        }

        return try {
            response.data!!.addGoalCompletion!!.let {
                Goal(it.title, it.id!!, Date(it.dueDate.toLong()), it.target!!, it.unit,
                    it.completions!!.map {comp ->
                        GoalCompletion(comp!!.name, it.id, Date(comp.date.toLong()))
                    }.toMutableList())
            }
        } catch(e : NullPointerException) {
            Log.e("ApolloGoalsRepo", "Failed null check when processing received data")
            null
        }
    }
}