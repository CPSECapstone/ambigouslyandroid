package edu.calpoly.flipted.businesslogic.tasks

abstract class Block(
    open val title : String?
)

data class QuizBlock(
    override val title: String?,
    val questions : List<Question>,
    val requiredQuestionsCorrect : Int,
    val id : Int
) : Block(title)

abstract class Question(
    open val question : String,
    open val pointValue : Int,
    open val id : Int
)

data class FreeResponseQuestion(
    override val question : String,
    override val pointValue : Int,
    override val id : Int
) : Question(question, pointValue, id)