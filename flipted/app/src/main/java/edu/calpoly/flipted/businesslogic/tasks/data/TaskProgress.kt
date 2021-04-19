package edu.calpoly.flipted.businesslogic.tasks.data

data class TaskProgress(
        val taskId: String,
        val finishedRequirements: List<String>
)