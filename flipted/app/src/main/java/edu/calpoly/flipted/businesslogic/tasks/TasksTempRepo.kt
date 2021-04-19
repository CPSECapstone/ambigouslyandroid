package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress

interface TasksTempRepo {
    suspend fun submitTaskProgress(taskProgress: TaskProgress) : String?
}