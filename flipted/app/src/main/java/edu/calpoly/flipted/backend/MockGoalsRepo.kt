package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalCompletion
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import java.text.SimpleDateFormat

class MockGoalsRepo : GoalsRepo {
    companion object {
        private val dateFormat = SimpleDateFormat("M-d-y")
        private var uid = 10
            get() {
                field += 1
                return field
            }
    }

    private val goals = listOf(
            Goal("Read 10 books", uid, dateFormat.parse("4-21-2021"), 10, "books", mutableListOf()),
            Goal("Complete 5 worksheets", uid, dateFormat.parse("6-18-2021"), 5, "worksheets", mutableListOf(
                    GoalCompletion("Worksheet A1", uid-1, dateFormat.parse("4-12-2021"))
            ))
    )

    override suspend fun getAllGoals(): List<Goal> = goals

    override suspend fun getGoalById(id: Int): Goal? = goals.first{it.uid == id}

    override suspend fun saveNewCompletion(completion: GoalCompletion): Goal? {
        val goal = goals.first{it.uid == completion.parentId}
        goal.completions.add(completion)
        return goal
    }
}