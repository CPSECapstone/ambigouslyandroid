package edu.calpoly.flipted.businesslogic.tasks.data


data class TaskRubricProgress(
    val finishedRequirements: List<RubricRequirement>,
    val task: Task
)