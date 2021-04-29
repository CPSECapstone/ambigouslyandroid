package edu.calpoly.flipted.businesslogic.quizzes.data

import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType

data class StudentAnswerInput(
        val questionId: String,
        val chosenAnswerValue: AnswerType
)