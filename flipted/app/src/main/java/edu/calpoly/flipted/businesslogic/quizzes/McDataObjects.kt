package edu.calpoly.flipted.businesslogic.quizzes

data class AnswerResult(
        val questionId : String,
        val correctAnswer : List<String>,
        val studentAnswer : String,
        val pointsAwarded : Int
)