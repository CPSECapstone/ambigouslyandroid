package edu.calpoly.flipted.businesslogic.mc_question

class GetAllQuestions {
    suspend fun getAllQuestions(): List<Question> {
        val answerOne = Answer("atomos", 1, isCorrect = false, isChecked = false)
        val answerTwo = Answer("neutron", 1, isCorrect = false, isChecked = false)
        val answerThree = Answer("nucleus", 1, isCorrect = true, isChecked = false)
        val answerFour = Answer("nucleon", 1, isCorrect = false, isChecked = false)
        val answerList = mutableListOf<Answer>(answerOne, answerTwo, answerThree, answerFour)
        val answer2One = Answer("atomos", 1, isCorrect = false, isChecked = false)
        val answer2Two = Answer("neutron", 1, isCorrect = false, isChecked = false)
        val answer2Three = Answer("nucleus", 1, isCorrect = true, isChecked = false)
        val answer2Four = Answer("nucleon", 1, isCorrect = false, isChecked = false)
        val answer2List = mutableListOf<Answer>(answer2One, answer2Two, answer2Three, answer2Four)

        return listOf(Question("The center of an atom is called the ______.", 1, answerList),
                Question("The center of an atom is called the ______.", 1, answer2List))
    }
}