package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskQuizAnswer
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

interface TasksRepo {
    suspend fun getTask(taskId: String) : Task
    suspend fun saveRubricProgress(progress: TaskRubricProgress)
    suspend fun saveQuizAnswer(answer: TaskQuizAnswer)
    suspend fun submitTask(taskId : String) : TaskSubmissionResult
}