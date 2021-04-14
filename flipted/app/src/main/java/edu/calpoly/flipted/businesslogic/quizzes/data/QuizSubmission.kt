package edu.calpoly.flipted.businesslogic.quizzes.data

data class QuizSubmission(
        val answers: List<StudentAnswerInput>,
        val answeredQuizUid: Int
)