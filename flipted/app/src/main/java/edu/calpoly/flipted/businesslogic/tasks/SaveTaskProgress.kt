package edu.calpoly.flipted.businesslogic.tasks

import edu.calpoly.flipted.businesslogic.errors.ResponseOrError
import edu.calpoly.flipted.businesslogic.tasks.data.QuizBlockStudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.data.TaskRubricProgress

class SaveTaskProgress(
        private val repo : TasksRepo
) {
    suspend fun saveRubricProgress(progress: TaskRubricProgress) {
        repo.saveRubricProgress(progress)
    }

    suspend fun saveQuizAnswer(answer: QuizBlockStudentAnswerInput) : ResponseOrError<QuizBlockStudentAnswerInput> {
        return repo.saveQuizAnswer(answer)
    }
}