package edu.calpoly.flipted.businesslogic.quizzes.data

import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType

abstract class StudentAnswerInput(
        val questionId: Int,
        val chosenAnswerValue: AnswerType
)