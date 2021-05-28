package edu.calpoly.flipted.backend

import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalsRepo
import edu.calpoly.flipted.businesslogic.goals.SubGoal
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
            listOf(
                    Goal(uids, "Read 10 books", dateFormat.parse("4-21-2021"), false, null, listOf(), "English", false, false, 10),
                    Goal(uids, "Complete 2 worksheets", dateFormat.parse("6-18-2021"), false, null, listOf(
                            SubGoal("Worksheet A1", dateFormat.parse("4-30-2021"), false, null),
                            SubGoal("Worksheet A2", dateFormat.parse("5-2-2021"), false, null)
                    ), "Classwork", false, true, null)
            ).associateByTo(goalsMap) { it.uid!! }
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

    override suspend fun editGoal(goal: Goal): Goal {
        delay(1000)
        val uid = goal.uid
        return if(uid == null) {
            Goal(uids, goal.title, goal.dueDate, goal.completed, goal.completedDate, goal.subGoals, goal.category, goal.favorited, goal.isOwnedByStudent, goal.pointValue).also {
                goalsMap[it.uid!!] = it
            }
        } else {
            if (!goalsMap.containsKey(goal.uid))
                throw IllegalArgumentException("No goal with uid ${goal.uid} exists")
            goalsMap[uid] = goal
            goal
        }
    }
}
