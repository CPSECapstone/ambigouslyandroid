package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress

class SaveTaskProgress(
        private val repo : TasksRepo
) {
    suspend fun execute(progress: TaskProgress) {
        repo.saveProgress(progress)
    }
}