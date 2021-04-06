package edu.calpoly.flipted.businesslogic.quizzes

interface QuestionsRepo {
    suspend fun getQuestionsForTask(taskId : Int) : List<Question>
}