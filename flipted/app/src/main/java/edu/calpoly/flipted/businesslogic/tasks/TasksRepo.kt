package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress

interface TasksRepo {
    suspend fun getTask(taskId: Int) : Task
    suspend fun saveProgress(progress: TaskProgress)
}