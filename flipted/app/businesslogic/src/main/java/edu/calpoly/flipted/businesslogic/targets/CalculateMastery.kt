package edu.calpoly.flipted.businesslogic.targets

import kotlin.math.round

object CalculateMastery {
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

    private fun combineMasteryScores(masteryScores: List<Int>) : Mastery {
        if(masteryScores.isEmpty())
            return Mastery.NOT_GRADED
        val avgScore = masteryScores.average()
        val roundedScore = round(avgScore).toInt()
        return intToMastery(roundedScore) ?: throw RuntimeException("Expected roundedScore to be 0, 1, or 2")
    }

    fun calculate(objectiveProgress: ObjectiveProgress) : Mastery {
        val masteryScores = objectiveProgress.tasks.mapNotNull { task -> masteryToInt(task.mastery) }
        return combineMasteryScores(masteryScores)
    }

    fun calculate(targetProgress: TargetProgress) : Mastery {
        val masteryScores = targetProgress.objectives.mapNotNull{ masteryToInt(calculate(it)) }
        return combineMasteryScores(masteryScores)
    }
}