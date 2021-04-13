package edu.calpoly.flipted.businesslogic.quizzes.data.questions

data class MultipleChoiceAnswerOption(
        val displayPrompt: String,
        val uid: Int
)

class MultipleChoiceQuestion(
        val options: List<MultipleChoiceAnswerOption>,
        question: String,
        pointValue: Int,
        uid: Int
) : Question(question, pointValue, uid)