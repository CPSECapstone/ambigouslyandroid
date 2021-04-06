package edu.calpoly.flipted.businesslogic.quizzes

class GetAllQuestions (private val repo : QuestionsRepo) {
    suspend fun execute(taskId : Int): List<Question> {
        return repo.getQuestionsForTask(taskId)
    }
}