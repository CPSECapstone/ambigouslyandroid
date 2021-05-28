package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.targets.TaskObjectiveProgress

class GetObjectiveProgress(private val repo: TasksRepo) {
    suspend fun execute(taskId: String): List<TaskObjectiveProgress> {
        return repo.getObjectiveProgress(taskId)
    }
}