package edu.calpoly.flipted.businesslogic.learningTargets

import edu.calpoly.flipted.type.Mastery


data class TargetProgress(
    val target: LearningTarget,
    val objectives: List<ObjectiveProgress>
)

data class ObjectiveProgress(
    val objectiveId: String,
    val objectiveName: String,
    val tasks: List<TaskObjectiveProgress>

)


data class TaskObjectiveProgress(
    val taskId: String,
    val taskName: String,
    val mastery: Mastery

)

enum class Mastery {
    NOT_GRADED,
    NOT_MASTERED,
    NEARLY_MASTERED,
    MASTERED
}

data class LearningTarget (
    val targetName: String
)

