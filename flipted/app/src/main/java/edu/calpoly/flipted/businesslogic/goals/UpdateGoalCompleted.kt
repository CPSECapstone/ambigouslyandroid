package edu.calpoly.flipted.businesslogic.goals

import java.util.*

/**
 * Marks a standalone goal as complete.
 *
 * Note: Goals with subgoals will be marked complete by the UpdateSubgoalCompleted use case.
 * This use case is only for use with standalone goals that do not have subgoals.
 */
object UpdateGoalCompleted {
    fun execute(goal: Goal, isComplete: Boolean) : Goal {
        if(goal.subGoals.isNotEmpty())
            throw IllegalArgumentException("Input goal has subgoals")

        if(isComplete == goal.completed)
            // We're not changing anything, so just bail instead of doing pointless work
            return goal

        return Goal(goal.uid, goal.title, goal.dueDate, isComplete, if(isComplete) Date() else null, goal.subGoals, goal.category, goal.favorited, goal.isOwnedByStudent, goal.pointValue)
    }
}