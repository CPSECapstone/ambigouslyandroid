package edu.calpoly.flipted.businesslogic.mc_question

class GetAllQuestions {
    suspend fun getAllQuestions(): List<Question> {
        val answerOne = Answer("atomos", 1, isCorrect = false, isChecked = false)
        val answerTwo = Answer("neutron", 1, isCorrect = false, isChecked = false)
        val answerThree = Answer("nucleus", 1, isCorrect = true, isChecked = false)
        val answerFour = Answer("nucleon", 1, isCorrect = false, isChecked = false)
        val answerList = mutableListOf<Answer>(answerOne, answerTwo, answerThree, answerFour)
        val answer2One = Answer("the nucleus", 1, isCorrect = false, isChecked = false)
        val answer2Two = Answer("tissue", 1, isCorrect = false, isChecked = false)
        val answer2Three = Answer("the cell", 1, isCorrect = true, isChecked = false)
        val answer2Four = Answer("the organ", 1, isCorrect = false, isChecked = false)
        val answer2Five = Answer("the atom", 1, isCorrect = false, isChecked = false)
        val answer2List = mutableListOf<Answer>(answer2One, answer2Two, answer2Three, answer2Four, answer2Five)

        return listOf(Question("The center of an atom is called the ______.", 1, answerList),
                Question("The basic unit of all organisms is ______.", 1, answer2List))
    }
}