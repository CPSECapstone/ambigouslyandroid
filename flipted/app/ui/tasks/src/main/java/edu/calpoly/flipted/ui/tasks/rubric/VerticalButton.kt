package edu.calpoly.flipted.ui.tasks.rubric

import android.content.Context
import android.graphics.Canvas
import android.text.TextPaint
import android.util.AttributeSet

class VerticalButton(context: Context?, attrs: AttributeSet?) :
    androidx.appcompat.widget.AppCompatButton(context!!, attrs) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(heightMeasureSpec, widthMeasureSpec)
        setMeasuredDimension(measuredHeight, measuredWidth)
    }

    override fun onDraw(canvas: Canvas) {
        val textPaint: TextPaint = paint
        textPaint.color = currentTextColor
        textPaint.drawableState = drawableState
        canvas.save()
        canvas.translate((0).toFloat(), height.toFloat())
        canvas.rotate((-90).toFloat())
        canvas.translate(compoundPaddingLeft.toFloat(), extendedPaddingTop.toFloat())
        layout.draw(canvas)
        canvas.restore()
    }
}