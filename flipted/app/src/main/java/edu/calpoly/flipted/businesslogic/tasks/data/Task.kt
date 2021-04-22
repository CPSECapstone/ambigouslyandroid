package edu.calpoly.flipted.businesslogic.tasks.data

import java.util.*

data class Task(
        val pages: List<Page>,
        val dueDate: Date,
        val title: String,
        val points: Int,
        val uid: String,
        val requirements: List<RubricRequirement>
)