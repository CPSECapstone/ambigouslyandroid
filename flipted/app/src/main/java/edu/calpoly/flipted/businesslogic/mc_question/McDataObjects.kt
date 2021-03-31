package edu.calpoly.flipted.businesslogic.mc_question

data class Question(
    val title : String,
    val uid : Int,
    val answers: MutableList<Answer>
)

data class Answer(
    val description : String,
    val parentId : Int,
    val isCorrect : Boolean,
    var isChecked : Boolean
)
