package edu.calpoly.flipted.businesslogic.quizzes.data.questions

import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.FreeResponseAnswer

class FreeResponseQuestion(
    question: String,
    pointValue: Int,
    uid: String,
    override val savedAnswer: FreeResponseAnswer?
) : Question(question, pointValue, uid)