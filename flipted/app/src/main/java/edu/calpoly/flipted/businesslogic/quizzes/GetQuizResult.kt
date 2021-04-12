package edu.calpoly.flipted.businesslogic.quizzes

class GetQuizResult(private val repo : QuestionsRepo) {
    suspend fun execute(resultId : Int) : QuizResult {
        return repo.getQuizResult(resultId)
    }
}