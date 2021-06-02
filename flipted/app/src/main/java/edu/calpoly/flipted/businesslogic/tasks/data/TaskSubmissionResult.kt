package edu.calpoly.flipted.businesslogic.tasks.data

import edu.calpoly.flipted.businesslogic.quizzes.AnswerResult
import edu.calpoly.flipted.businesslogic.quizzes.data.answers.AnswerType
import java.util.*

data class TaskSubmissionResult(
        val taskId: String,
        val graded: Boolean,
        val pointsAwarded: Int,
        val pointsPossible: Int,
        val teacherComment: String?,
        val results: List<AnswerResult>
)