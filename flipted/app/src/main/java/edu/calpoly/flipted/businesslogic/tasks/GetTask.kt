package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.Task

class GetTask(
    private val repo : TasksRepo
) {
    suspend fun execute(taskId : Int) : Task = repo.getTask(taskId)
}