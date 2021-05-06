package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.goals.*
import kotlinx.coroutines.delay
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
        private val goalsMap : MutableMap<String, Goal> = mutableMapOf()

        init {
            uids.also {goalsMap[it] = Goal("Read 10 books", it, dateFormat.parse("4-21-2021"), null, listOf(), false, "English", false, false, null) }
            uids.also {goalsMap[it] = Goal("Complete 5 worksheets", it, dateFormat.parse("6-18-2021"), null, listOf(
                    SubGoal("Worksheet A1", uids, dateFormat.parse("4-30-2021"), true, dateFormat.parse("4-27-2021")),
                    SubGoal("Worksheet A2", uids, dateFormat.parse("5-2-2021"), false, null)
            ), false, "Classwork", false, false, 10)}
        }
    }



    override suspend fun getAllGoals(): List<Goal> {
        delay(1000)
        return goalsMap.values.toList()
    }

    override suspend fun getGoalById(id: String): Goal {
        delay(1000)
        return goalsMap[id] ?: throw IllegalArgumentException("No goal with uid $uid exists")
    }

    override suspend fun saveNewGoal(goal: UnsavedNewGoal): Goal {
        delay(1000)
        return Goal(
            goal.title,
            uids,
            goal.dueDate,
            null,
            goal.subGoals.map {
                SubGoal(it.title, uids, it.dueDate, false, null)
            },
            false,
            goal.category,
            goal.favorited,
            goal.ownedByStudent,
            goal.pointValue).also {
                goalsMap[it.uid] = it
        }
    }

    override suspend fun editGoal(goal: Goal): Goal {
        delay(1000)
        if(!goalsMap.containsKey(goal.uid))
            throw IllegalArgumentException("No goal with uid ${goal.uid} exists")
        goalsMap[goal.uid] = goal
        return goal
    }
}
