package com.example.flipteded.backend

import android.util.Log
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.AllGoalsQuery
import com.example.flipteded.businesslogic.goals.Goal
import com.example.flipteded.businesslogic.goals.GoalCompletion
import com.example.flipteded.businesslogic.goals.GoalsRepo
import java.util.*

class ApolloGoalsRepo : GoalsRepo {

    val apolloClient = ApolloClient.builder()
        .serverUrl("https://your.domain/graphql/endpoint")
        .build()

    override suspend fun getAllGoals(): List<Goal> {
        val response = try {
            apolloClient.query(AllGoalsQuery()).await()
        } catch(e: ApolloException) {
            Log.e("ApolloGoalsRepo", "Error when querying backend: ${e.message}")
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
        TODO("Not yet implemented")
    }
}