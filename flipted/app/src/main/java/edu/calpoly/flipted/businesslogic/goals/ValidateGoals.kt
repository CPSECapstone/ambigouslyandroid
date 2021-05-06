package edu.calpoly.flipted.businesslogic.goals

import java.util.*

object ValidateGoals {
    fun execute(goal: UnsavedNewGoal) : List<String> {
        val errors = mutableListOf<String>()
        if(goal.title.isBlank())
            errors.add("Title cannot be empty")
        if(goal.dueDate <= Date())
            errors.add("Due date cannot be in the past")
        return errors
    }
}