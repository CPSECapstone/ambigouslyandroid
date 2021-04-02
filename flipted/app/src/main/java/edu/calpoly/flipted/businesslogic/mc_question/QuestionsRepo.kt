package edu.calpoly.flipted.businesslogic.mc_question

interface QuestionsRepo {
    suspend fun getAllQuestions() : List<Question>
}