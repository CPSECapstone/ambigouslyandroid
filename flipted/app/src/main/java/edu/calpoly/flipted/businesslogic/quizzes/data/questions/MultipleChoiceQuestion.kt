package edu.calpoly.flipted.businesslogic.quizzes.data.questions

import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.MultipleChoiceAnswer

data class MultipleChoiceAnswerOption(
        val displayPrompt: String,
        val id: Int
)

class MultipleChoiceQuestion(
        val options: List<MultipleChoiceAnswerOption>,
        question: String,
        pointValue: Int,
        uid: String,
        override val savedAnswer: MultipleChoiceAnswer?
) : Question(question, pointValue, uid)