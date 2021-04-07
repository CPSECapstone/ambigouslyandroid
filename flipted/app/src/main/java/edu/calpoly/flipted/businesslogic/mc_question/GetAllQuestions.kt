package edu.calpoly.flipted.businesslogic.mc_question

class GetAllQuestions (private val repo : QuestionsRepo) {
    suspend fun execute(id : String): List<McQuestion> {
        return repo.getAllQuestions(id);
    }


}