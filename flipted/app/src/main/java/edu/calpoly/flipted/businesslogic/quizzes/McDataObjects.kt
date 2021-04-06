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
    val isCorrect : Boolean,
    var isChecked : Boolean
)