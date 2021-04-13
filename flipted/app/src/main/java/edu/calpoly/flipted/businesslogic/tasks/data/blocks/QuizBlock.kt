package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.quizzes.data.questions.Question
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class QuizBlock(
        val questions: List<Question>,
        val requiredQuestionsCorrect: Int,
        override val requirement: RubricRequirement,
        title: String? = null
) : TaskBlock(requirement, title) {
        override val completed: TaskBlock
                get() = QuizBlock(questions, requiredQuestionsCorrect, requirement.completed, title)
        override val incompleted: TaskBlock
                get() = QuizBlock(questions, requiredQuestionsCorrect, requirement.incompleted, title)
}