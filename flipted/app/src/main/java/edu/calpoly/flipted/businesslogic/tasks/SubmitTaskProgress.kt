package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress

class SubmitTaskProgress(private val repo: TasksTempRepo) {
    suspend fun execute(taskProgress : TaskProgress) : String? = repo.submitTaskProgress(taskProgress)
}