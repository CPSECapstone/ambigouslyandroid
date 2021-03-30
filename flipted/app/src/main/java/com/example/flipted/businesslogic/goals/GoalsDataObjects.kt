package com.example.flipted.businesslogic.goals

import java.util.Date

data class Goal(
    val title : String,
    val uid : Int,
    val dueDate : Date,
    val targetCompletionCount : Int,
    val unitOfMeasurement : String,
    val completions: MutableList<GoalCompletion>
)

data class GoalCompletion(
    val description : String,
    val parentId : Int,
    val completedDate : Date
)
