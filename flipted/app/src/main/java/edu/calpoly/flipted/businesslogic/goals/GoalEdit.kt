package edu.calpoly.flipted.businesslogic.goals
import edu.calpoly.flipted.ui.goals.GoalNewFragment


class GoalEdit

fun isValidNewGoal(newGoalInput: NewGoalInput): Boolean {

    return newGoalInput.title.isNotEmpty()

}

