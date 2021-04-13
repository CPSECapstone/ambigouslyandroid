package edu.calpoly.flipted.businesslogic.tasks.data

data class TaskProgress(
    val finishedRequirements: List<RubricRequirement>,
    val task: Task
)