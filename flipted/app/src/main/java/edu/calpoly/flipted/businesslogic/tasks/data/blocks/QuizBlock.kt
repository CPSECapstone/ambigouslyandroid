package edu.calpoly.flipted.businesslogic.tasks.data.blocks

import edu.calpoly.flipted.businesslogic.quizzes.data.questions.Question
import edu.calpoly.flipted.businesslogic.tasks.data.RubricRequirement

class QuizBlock(
        val questions: List<Question>,
        val requiredQuestionsCorrect: Int,
        requirement: RubricRequirement,
        title: String?
) : TaskBlock(requirement, title)