package com.example.howcovidspoilemylife

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View

class CustomButton @JvmOverloads constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    companion object {
        private const val DEFAULT_FACE_COLOR = Color.YELLOW
        private const val DEFAULT_EYES_COLOR = Color.BLACK
        private const val DEFAULT_MOUTH_COLOR = Color.BLACK

        const val HAPPY = 0L
        const val SAD = 1L
    }

    private var faceColor = DEFAULT_FACE_COLOR
    private var noFace = Color.BLACK
    private val paint = Paint()
    private val size = 170f
    private val leftEyeRect = RectF(size * 0.36f, size * 0.23f, size * 0.46f, size * 0.50f)
    private val rightEyeRect = RectF(size * 0.84f, size * 0.23f, size * 0.72f, size * 0.50f)
    private val mouthPath = Path()
    var happinessState = HAPPY
        set(state) {
            field = state
            invalidate()
        }

    init {
        paint.isAntiAlias = true
        setupAttributes(attributeSet)
    }

    private fun setupAttributes(attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomButton,
            0, 0)


        happinessState =
            typedArray.getInt(R.styleable.CustomButton_state, HAPPY.toInt()).toLong()
        faceColor = typedArray.getColor(R.styleable.CustomButton_faceColor, DEFAULT_FACE_COLOR)
        noFace = typedArray.getColor(R.styleable.CustomButton_eyesColor, DEFAULT_EYES_COLOR)
        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)
    }

    private fun drawMouth(canvas: Canvas) {
        mouthPath.reset()
        if (happinessState == HAPPY) {
            mouthPath.moveTo(size * 0.22f, size * 0.7f)
            mouthPath.quadTo(size * 0.7f, size * 0.80f, size * 1f, size * 0.7f)
            mouthPath.quadTo(size * 0.6f, size * 1f, size * 0.22f, size * 0.7f)
        } else {
            mouthPath.moveTo(size * 0.25f, size * 0.8f)
            mouthPath.quadTo(size * 0.7f, size * 0.7f, size * 1f, size * 0.85f)
            mouthPath.quadTo(size * 0.8f, size * 0.7f, size * 0.35f, size * 0.76f)
        }
        paint.color = noFace
        canvas.drawPath(mouthPath, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        paint.color = noFace
        paint.style = Paint.Style.FILL
        canvas.drawOval(leftEyeRect, paint)
        canvas.drawOval(rightEyeRect, paint)
    }

    private fun drawFaceBackground(canvas: Canvas) {
        paint.color = faceColor
        paint.style = Paint.Style.FILL
        canvas.drawCircle(100f, 100f, 100f, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(200, 200)
    }
}