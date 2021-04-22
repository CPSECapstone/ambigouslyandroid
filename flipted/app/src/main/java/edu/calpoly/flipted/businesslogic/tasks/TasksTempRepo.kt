package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress

interface TasksTempRepo {
    suspend fun submitTaskProgress(taskProgress: TaskRubricProgress) : String?
}