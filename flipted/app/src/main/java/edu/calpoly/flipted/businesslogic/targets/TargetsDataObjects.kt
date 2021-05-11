package edu.calpoly.flipted.businesslogic.targets

data class LearningTarget(
        val targetId: String,
        val targetName: String
)

enum class Mastery {
    NOT_GRADED,
    NOT_MASTERED,
    NEARLY_MASTERED,
    MASTERED
}

data class TaskObjectiveProgress(
        val taskId: String,
        val taskName: String,
        val mastery: Mastery
)

data class ObjectiveProgress(
        val objectiveId: String,
        val objectiveName: String,
        val tasks: List<TaskObjectiveProgress>
)

data class TargetProgress(
        val target: LearningTarget,
        val objectives: List<ObjectiveProgress>,
        val studentId: String
)

