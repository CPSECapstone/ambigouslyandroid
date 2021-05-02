package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import edu.calpoly.flipted.businesslogic.goals.NewGoalInput

class ApolloGoalsRepo : ApolloRepo(), GoalsRepo {

    override suspend fun getAllGoals(): List<Goal> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoalById(id: String): Goal {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewGoal(goal: NewGoalInput): Goal {
        TODO("Not yet implemented")
    }

    override suspend fun editGoal(goal: Goal): Goal {
        TODO("Not yet implemented")
    }
}