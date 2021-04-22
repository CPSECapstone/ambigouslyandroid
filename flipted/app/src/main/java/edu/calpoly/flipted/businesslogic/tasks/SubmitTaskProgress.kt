package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress

class SubmitTaskProgress(private val repo: TasksTempRepo) {
    suspend fun execute(taskProgress : TaskRubricProgress) : String? = repo.submitTaskProgress(taskProgress)
}