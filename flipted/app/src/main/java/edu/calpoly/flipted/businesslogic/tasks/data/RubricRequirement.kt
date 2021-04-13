package edu.calpoly.flipted.businesslogic.tasks.data

data class RubricRequirement(
        val description: String,
        val isComplete: Boolean,
        val pointValue: Int,
        val uid: Int
)