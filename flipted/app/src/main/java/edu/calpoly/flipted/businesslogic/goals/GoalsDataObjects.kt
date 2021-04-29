package edu.calpoly.flipted.businesslogic.goals

import java.util.Date

data class Goal(
    val title : String,
    val uid : String,
    val dueDate : Date,
    val completedDate: Date?,
    val subgoals: List<SubGoal>,
    val completed: Boolean,
    val ownedByStudent: Boolean
)

data class SubGoal(
        val title: String,
        val id: String,
        val dueDate: Date,
        val completed: Boolean,
        val completedDate: Date?
)

data class NewGoalInput(
    val title: String,
    val dueDate: Date,
    val subGoals: List<SubGoalInput>,
    val category: String,
    val favorited: Boolean,
    val ownedByStudent: Boolean,
    val pointValue: Int?
)

data class SubGoalInput(
    val title: String,
    val dueDate: Date
)


