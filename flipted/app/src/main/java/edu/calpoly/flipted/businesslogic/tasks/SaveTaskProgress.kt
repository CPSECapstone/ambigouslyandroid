package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.tasks.data.TaskQuizAnswer
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress

class SaveTaskProgress(
        private val repo : TasksRepo
) {
    suspend fun saveRubricProgress(progress: TaskRubricProgress) {
        repo.saveRubricProgress(progress)
    }

    suspend fun saveQuizAnswer(answer: TaskQuizAnswer) {
        repo.saveQuizAnswer(answer)
    }
}