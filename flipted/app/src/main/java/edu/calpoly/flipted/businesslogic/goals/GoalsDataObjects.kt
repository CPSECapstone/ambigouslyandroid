package edu.calpoly.flipted.businesslogic.goals

import java.util.Date

open class UnsavedNewGoal(
        val title: String,
        val dueDate: Date,
        open val subGoals: List<UnsavedNewSubGoal>,
        val category: String,
        val favorited: Boolean,
        val ownedByStudent: Boolean,
        val pointValue: Int?
)

class Goal(
    title : String,
    val uid : String,
    dueDate : Date,
    val completedDate: Date?,
    override val subGoals: List<SubGoal>,
    val completed: Boolean,
    category: String,
    favorited: Boolean,
    ownedByStudent: Boolean,
    pointValue: Int?
) : UnsavedNewGoal(
        title,
        dueDate,
        subGoals,
        category,
        favorited,
        ownedByStudent,
        pointValue
)

open class UnsavedNewSubGoal(
        val title: String,
        val dueDate: Date
)

class SubGoal(
        title: String,
        val id: String,
        dueDate: Date,
        val completed: Boolean,
        val completedDate: Date?
) : UnsavedNewSubGoal(title, dueDate)




