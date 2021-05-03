package edu.calpoly.flipted.businesslogic.goals

import java.util.*

/**
 * Marks a standalone goal as complete.
 *
 * Note: Goals with subgoals will be marked complete by the UpdateSubgoalCompleted use case.
 * This use case is only for use with standalone goals that do not have subgoals.
 */
class UpdateGoalCompleted(private val repo: GoalsRepo) {
    suspend fun execute(goal: Goal, isComplete: Boolean) : Goal {
        if(goal.subGoals.isNotEmpty())
            throw IllegalArgumentException("Input goal has subgoals")

        if(isComplete == goal.completed)
            // We're not changing anything, so just bail instead of doing pointless work
            return goal

        val updatedGoal = if(isComplete)
            Goal(goal.title, goal.uid, goal.dueDate, Date(), goal.subGoals, true, goal.category, goal.favorited, goal.ownedByStudent, goal.pointValue)
        else
            Goal(goal.title, goal.uid, goal.dueDate, null, goal.subGoals, false, goal.category, goal.favorited, goal.ownedByStudent, goal.pointValue)
        return repo.editGoal(updatedGoal)
    }
}