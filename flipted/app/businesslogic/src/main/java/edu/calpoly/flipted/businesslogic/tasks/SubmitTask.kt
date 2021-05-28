package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.Task
import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

class SubmitTask(private val repo : TasksRepo) {
    suspend fun execute(taskId : String) : TaskSubmissionResult = repo.submitTask(taskId)
}