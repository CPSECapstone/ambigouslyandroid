package edu.calpoly.flipted.businesslogic.missions

import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

data class Mission(
    val uid: String,
    val name: String
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