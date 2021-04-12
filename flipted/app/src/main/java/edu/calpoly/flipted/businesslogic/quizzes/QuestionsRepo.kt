package edu.calpoly.flipted.businesslogic.quizzes

interface QuestionsRepo {
    suspend fun getQuiz(quizId : Int) : Quiz
    suspend fun saveCompletionForQuiz(quiz : Quiz) : QuizResult
    suspend fun getQuizResult(resultId : Int) : QuizResult
}