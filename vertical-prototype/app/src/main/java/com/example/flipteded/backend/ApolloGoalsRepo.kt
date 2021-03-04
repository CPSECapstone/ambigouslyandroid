package com.example.flipteded.backend

import com.example.flipteded.businesslogic.goals.Goal
import com.example.flipteded.businesslogic.goals.GoalCompletion
import com.example.flipteded.businesslogic.goals.GoalsRepo

class ApolloGoalsRepo : GoalsRepo {
    override suspend fun getAllGoals(): List<Goal> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoalById(id: Int): Goal? {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewCompletion(completion: GoalCompletion) {
        TODO("Not yet implemented")
    }
}