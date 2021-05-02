package edu.calpoly.flipted.businesslogic.goals

import java.util.*

/**
 * Marks a subgoal goal as complete.
 * If marking the subgoal as complete causes all subgoals of the goal to be complete, then the
 * parent goal is also marked as complete.
 */
class UpdateSubgoalCompleted(private val repo: GoalsRepo) {
    suspend fun execute(goal: Goal, subGoal: SubGoal, isComplete: Boolean) : Goal {
        if(!goal.subgoals.contains(subGoal))
            throw IllegalArgumentException("The subgoal with id ${subGoal.id} is not a child of the goal with uid ${goal.uid}")

        if(subGoal.completed == isComplete)
            // We're not changing anything, so just bail instead of doing pointless work
            return goal

        val subgoals = goal.subgoals.map {
            if(it.id == subGoal.id)
                SubGoal(it.title, it.id, it.dueDate, isComplete, if(isComplete) Date() else null)
            else it
        }
        val allSubgoalsCompleted = subgoals.fold(true) {completed, subgoal -> completed && subgoal.completed}
        val updatedGoal = Goal(goal.title, goal.uid, goal.dueDate, if(allSubgoalsCompleted) Date() else null, subgoals, allSubgoalsCompleted, goal.ownedByStudent)
        return repo.editGoal(updatedGoal)
    }
}