package edu.calpoly.flipted.ui

import edu.calpoly.flipted.R
import edu.calpoly.flipted.type.Mastery

object MasteryResources {
    fun colorResource(mastery: Mastery) : Int = when(mastery) {
        Mastery.NOT_GRADED -> R.color.gray3
        Mastery.NOT_MASTERED -> R.color.notMastered
        Mastery.NEARLY_MASTERED -> R.color.nearlyMastered
        Mastery.MASTERED -> R.color.mastered
        else -> 0
    }

    fun stringResource(mastery: Mastery) : Int = when(mastery) {
        Mastery.NOT_GRADED -> R.string.mastery_not_graded
        Mastery.NOT_MASTERED -> R.string.mastery_not_mastered
        Mastery.NEARLY_MASTERED -> R.string.mastery_nearly_mastered
        Mastery.MASTERED -> R.string.mastery_mastered
        else -> -1
    }
}