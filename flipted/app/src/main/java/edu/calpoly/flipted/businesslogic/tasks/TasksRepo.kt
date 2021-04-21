package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskProgress
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

interface TasksRepo {
    suspend fun getTask(taskId: Int) : Task
    suspend fun saveProgress(progress: TaskProgress)
    //suspend fun submitQuestion(input: QuestionInput)
    suspend fun submitTask(taskId : String) : TaskSubmissionResult
}