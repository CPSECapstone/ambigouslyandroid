package edu.calpoly.flipted.businesslogic.tasks.data

import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput

data class TaskQuizAnswer(
    val answer: StudentAnswerInput,
    val task: Task
)