package edu.calpoly.flipted.ui.missions

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.children
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import edu.calpoly.flipted.R

class MissionTasksItemDecoration(private val fragment: Fragment) : RecyclerView.ItemDecoration() {
    companion object {
        private const val ITEM_SEPARATION = 85F
    }

    private fun dpToPx(dp: Float) : Float {
        return dp * fragment.resources.displayMetrics.density
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {

        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val color = ResourcesCompat.getColor(fragment.resources, R.color.blue1, null)
        val paint = Paint()
        paint.color = color
        paint.strokeWidth = dpToPx(5F)

        parent.children.forEach { child ->
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin;
            val bottom = top + dpToPx(ITEM_SEPARATION).toInt()
            c.drawLine(dpToPx(117.5F),  top.toFloat(), dpToPx(117.5F), bottom.toFloat(), paint)
        }

    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        if(position != 0)
            outRect.top = dpToPx(ITEM_SEPARATION).toInt()
    }
}