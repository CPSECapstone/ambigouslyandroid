package edu.calpoly.flipted.businesslogic.mc_question

import java.util.Date

data class Quiz(
    val uid : String,
    val name : String,
    val course : String,
    val instructions : String,
    val due : Date,
    val questions : McQuestion
)

data class McQuestion(
    val description : String,
    val uid : String,
    val taskId : String,
    val options: List<Option>,
    val answers: List<String>?,
    val points: Int
)

data class Option(
    val parentId : Int,
    val description : String
)

data class QuizSubmissionInput(
    val student: String,
    val quizId: String,
    val answers: MutableList<AnswerInput>
)

data class AnswerInput(
    val questionId: String,
    val choices: MutableList<String>
)
