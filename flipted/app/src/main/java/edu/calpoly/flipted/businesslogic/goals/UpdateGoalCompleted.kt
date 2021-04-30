package edu.calpoly.flipted.businesslogic.goals

import java.util.*

/**
 * Marks a standalone goal as complete.
 *
 * Note: Goals with subgoals will be marked complete by the UpdateSubgoalCompleted use case.
 * This use case is only for use with standalone goals that do not have subgoals.
 */
class UpdateGoalCompleted(private val repo: GoalsRepo) {
    suspend fun execute(goal: Goal, isComplete: Boolean) {
        if(goal.subgoals.isNotEmpty())
            throw IllegalArgumentException("Input goal has subgoals")

        if(isComplete == goal.completed)
            // We're not changing anything, so just bail instead of doing pointless work
            return

        val updatedGoal = if(isComplete)
            Goal(goal.title, goal.uid, goal.dueDate, Date(), goal.subgoals, true, goal.ownedByStudent)
        else
            Goal(goal.title, goal.uid, goal.dueDate, null, goal.subgoals, false, goal.ownedByStudent)
        repo.editGoal(updatedGoal)
    }
}