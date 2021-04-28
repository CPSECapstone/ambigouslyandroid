package edu.calpoly.flipted.businesslogic.quizzes.data.questions

data class MultipleChoiceAnswerOption(
        val displayPrompt: String,
        val id: Int
)

class MultipleChoiceQuestion(
        val options: List<MultipleChoiceAnswerOption>,
        question: String,
        pointValue: Int,
        uid: String
) : Question(question, pointValue, uid)