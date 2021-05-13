package edu.calpoly.flipted.businesslogic.targets

import kotlin.math.round

object CalculateObjectiveMastery {
    private fun masteryToInt(mastery: Mastery) : Int? = when(mastery) {
        Mastery.NOT_GRADED -> null
        Mastery.NOT_MASTERED -> 0
        Mastery.NEARLY_MASTERED -> 1
        Mastery.MASTERED -> 2
    }

    private fun intToMastery(int: Int) : Mastery? = when(int) {
        0 -> Mastery.NOT_MASTERED
        1 -> Mastery.NEARLY_MASTERED
        2 -> Mastery.MASTERED
        else -> null
    }

    fun execute(objectiveProgress: ObjectiveProgress) : Mastery {
        val masteryScores = objectiveProgress.tasks.mapNotNull { task -> masteryToInt(task.mastery) }
        val avgScore = masteryScores.average()
        val roundedScore = round(avgScore).toInt()
        return intToMastery(roundedScore) ?: throw RuntimeException()
    }
}