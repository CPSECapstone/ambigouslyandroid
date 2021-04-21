package edu.calpoly.flipted.businesslogic.tasks.data

import edu.calpoly.flipted.businesslogic.quizzes.data.StudentAnswerInput

data class TaskProgress(
    val finishedRequirements: List<RubricRequirement>,
    val answeredQuestions: List<StudentAnswerInput>,
    val task: Task
)