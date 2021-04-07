package edu.calpoly.flipted.businesslogic.quizzes

class GetQuiz (private val repo : QuestionsRepo) {
    suspend fun execute(taskId : Int): Quiz {
        return repo.getQuiz(taskId)
    }
}