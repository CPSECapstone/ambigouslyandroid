package edu.calpoly.flipted.ui.myProgress

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import edu.calpoly.flipted.R
import edu.calpoly.flipted.businesslogic.progress.Mastery

class MasteryStateIndicator(context: Context, attrSet: AttributeSet) : AppCompatImageView(context, attrSet) {

    var state: Mastery = Mastery.NOT_GRADED

    init {
        setImageResource(R.drawable.mastery_state_indicator_states)
    }

    companion object {
        private val STATE_NOT_GRADED = intArrayOf(R.attr.state_not_graded)
        private val STATE_NOT_MASTERED = intArrayOf(R.attr.state_not_mastered)
        private val STATE_NEARLY_MASTERED = intArrayOf(R.attr.state_nearly_mastered)
        private val STATE_MASTERED = intArrayOf(R.attr.state_mastered)
    }



    override fun onCreateDrawableState(extraSpace: Int): IntArray {
        val drawableState = super.onCreateDrawableState(extraSpace + 1)
        val addDrawableState = when(state) {
            Mastery.NOT_GRADED -> STATE_NOT_GRADED
            Mastery.NOT_MASTERED -> STATE_NOT_MASTERED
            Mastery.NEARLY_MASTERED -> STATE_NEARLY_MASTERED
            Mastery.MASTERED -> STATE_MASTERED
        }
        mergeDrawableStates(drawableState, addDrawableState)
        return drawableState
    }
}