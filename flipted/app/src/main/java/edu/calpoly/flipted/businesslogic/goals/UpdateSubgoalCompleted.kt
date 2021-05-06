package edu.calpoly.flipted.businesslogic.goals

import java.util.*

/**
 * Marks a subgoal goal as complete.
 * If marking the subgoal as complete causes all subgoals of the goal to be complete, then the
 * parent goal is also marked as complete.
 */
object UpdateSubgoalCompleted {
    fun execute(goal: Goal, subGoal: SubGoal, isComplete: Boolean) : Goal {
        if(!goal.subGoals.contains(subGoal))
            throw IllegalArgumentException("The provided subgoal is not a child of the goal with uid ${goal.uid}")

        if(subGoal.completed == isComplete)
            // We're not changing anything, so just bail instead of doing pointless work
            return goal

        val subgoals = goal.subGoals.map {
            if(it == subGoal)
                SubGoal(it.title, it.dueDate, isComplete, if(isComplete) Date() else null, it.stableId)
            else it
        }
        val allSubgoalsCompleted = subgoals.fold(true) {completed, subgoal -> completed && subgoal.completed}
        return Goal(goal.uid, goal.title, goal.dueDate, allSubgoalsCompleted, if(allSubgoalsCompleted) Date() else null, subgoals, goal.category, goal.favorited, goal.isOwnedByStudent, goal.pointValue)
    }
}