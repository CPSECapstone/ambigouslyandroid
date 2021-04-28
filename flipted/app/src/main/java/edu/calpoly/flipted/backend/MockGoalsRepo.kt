package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import edu.calpoly.flipted.businesslogic.goals.SubGoal
import java.text.SimpleDateFormat

class MockGoalsRepo : GoalsRepo {
    companion object {
        private val dateFormat = SimpleDateFormat("M-d-y")
        private var uid = 10
            get() {
                field += 1
                return field
            }
        private val uids
            get() = uid.toString()
    }

    private val goals = listOf(
            Goal("Read 10 books", uids, dateFormat.parse("4-21-2021"), null, listOf(), false, true),
            Goal("Complete 5 worksheets", uids, dateFormat.parse("6-18-2021"), null, listOf(
                    SubGoal("Worksheet A1", uids, dateFormat.parse("4-30-2021"), true, dateFormat.parse("4-27-2021")),
                    SubGoal("Worksheet A2", uids, dateFormat.parse("5-2-2021"), false, null)
            ), false, false)
    )

    override suspend fun getAllGoals(): List<Goal> = goals

    override suspend fun getGoalById(id: String): Goal? = goals.first{it.uid == id}

    override suspend fun saveNewGoal(goal: Goal): Goal {
        TODO("Not yet implemented")
    }

    override suspend fun editGoal(goal: Goal): Goal {
        TODO("Not yet implemented")
    }
}