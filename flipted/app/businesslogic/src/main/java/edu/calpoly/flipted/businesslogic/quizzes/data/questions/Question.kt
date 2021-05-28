package edu.calpoly.flipted.businesslogic.quizzes.data.questions

import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType

abstract class Question(
        val question: String,
        val pointValue: Int,
        val uid: String
) {
    abstract val savedAnswer: AnswerType?
}