package edu.calpoly.flipted.backend

import android.util.Log
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
//import edu.calpoly.flipted.AllGoalsQuery
//import edu.calpoly.flipted.SaveCompletionMutation
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalCompletion
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import java.util.*

class ApolloGoalsRepo : ApolloRepo(), GoalsRepo {

    override suspend fun getAllGoals(): List<Goal> {
        TODO("Not yet implemented")
    }

    override suspend fun getGoalById(id: Int): Goal? {
        TODO("Not yet implemented")
    }

    override suspend fun saveNewCompletion(completion: GoalCompletion): Goal? {
        TODO("Not yet implemented")
    }
}
