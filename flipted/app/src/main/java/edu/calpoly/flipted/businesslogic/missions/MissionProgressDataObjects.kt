package edu.calpoly.flipted.businesslogic.missions

import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult
import java.util.*

data class Mission(
    val uid: String,
    val name: String,
    val description: String,
    val content: List<SparseTask>
)

data class SparseTask(
    val id: String,
    val name: String,
    val instructions: String,
    val points: Int,
    val dueDate: Date?
)

data class TaskStats(
    val task: SparseTask,
    val submission: TaskSubmissionResult?
)

data class MissionProgress(
    val mission: Mission,
    val progress: List<TaskStats>
)