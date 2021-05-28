package edu.calpoly.flipted.businesslogic.tasks.data

import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput
import edu.calpoly.flipted.businesslogic.tasks.data.blocks.QuizBlock

data class TaskQuizAnswer(
    val answer: StudentAnswerInput,
    val task: Task,
    val block: QuizBlock
)