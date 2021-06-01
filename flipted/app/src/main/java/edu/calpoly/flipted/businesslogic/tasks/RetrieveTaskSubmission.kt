package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskSubmissionResult

class RetrieveTaskSubmission(private val repo : TasksRepo) {
    suspend fun execute(taskId : String) : TaskSubmissionResult = repo.retrieveTaskSubmission(taskId)
}