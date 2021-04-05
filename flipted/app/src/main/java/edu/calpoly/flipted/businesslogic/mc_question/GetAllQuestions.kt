package edu.calpoly.flipted.businesslogic.mc_question

class GetAllQuestions (private val repo : QuestionsRepo) {
    suspend fun execute(id : Int?): List<Question> {
        return repo.getAllQuestions();
    }
}