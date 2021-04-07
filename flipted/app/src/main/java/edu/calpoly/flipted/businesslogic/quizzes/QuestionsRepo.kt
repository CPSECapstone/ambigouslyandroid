package edu.calpoly.flipted.businesslogic.quizzes

interface QuestionsRepo {
    suspend fun getQuestionsForTask(taskId : Int) : Quiz
    suspend fun saveCompletionForQuiz(quiz : Quiz)
}