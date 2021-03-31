package edu.calpoly.flipted.businesslogic.mc_question

class GetAllQuestions {
    suspend fun getAllQuestions(): List<Question> {
        val answerOne = Answer("atomos", 1, isCorrect = false, isChecked = false)
        val answerTwo = Answer("neutron", 1, isCorrect = false, isChecked = false)
        val answerThree = Answer("nucleus", 1, isCorrect = true, isChecked = false)
        val answerFour = Answer("nucleon", 1, isCorrect = false, isChecked = false)
        val answerList = mutableListOf<Answer>(answerOne, answerTwo, answerThree, answerFour)

        return listOf(Question("The center of an atom is called the ______.", 1, answerList))
    }
}