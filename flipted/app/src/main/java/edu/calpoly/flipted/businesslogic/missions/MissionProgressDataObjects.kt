package edu.calpoly.flipted.businesslogic.missions

import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

data class Mission(
    val id: String,
    val course: String,
    val name: String,
    val description: String
)

data class TaskStats(
    val taskId: String,
    val name: String,
    val submission: TaskSubmissionResult?
)

data class MissionProgress(
    val mission: Mission,
    val progress: List<TaskStats>
)