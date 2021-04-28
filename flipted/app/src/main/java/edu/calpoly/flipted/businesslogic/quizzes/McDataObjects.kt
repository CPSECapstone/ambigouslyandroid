package edu.calpoly.flipted.businesslogic.quizzes

data class Question(
    val title : String,
    val uid : Int,
    val taskId : Int,
    val answers: MutableList<Answer>
)

data class Answer(
    val description : String,
    val parentId : Int,
    var isChecked : Boolean,
    val isCorrect : Boolean? = null
)

data class Quiz(
    val uid : Int,
    val questions: List<Question>
)

data class QuizResult(
    val uid : Int,
    val questions : List<Question>,
    val numCorrect : Int
)

data class AnswerResult(
        val questionId : String,
        val correctAnswer : List<String>,
        val studentAnswer : String,
        val pointsAwarded : Int
)