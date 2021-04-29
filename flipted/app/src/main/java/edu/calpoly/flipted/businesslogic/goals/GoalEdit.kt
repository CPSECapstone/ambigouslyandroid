package edu.calpoly.flipted.businesslogic.goals


class GoalEdit

fun isValidNewGoal(newGoalInput: NewGoalInput): Boolean {

    return newGoalInput.title.isNotEmpty()

}

