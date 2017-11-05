package com.djekgrif.fastcleaner.ui.widgets

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.os.Parcelable
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import android.view.animation.Animation
import android.view.animation.Transformation
import com.djekgrif.fastcleaner.R
import com.djekgrif.fastcleaner.ui.UiUtils

/**
 * Created by djek-grif on 10/19/17.
 */
class CustomProgress : View {

    companion object {
        const private val INSTANCE_STATE = "saved_instance"
        const private val INSTANCE_STROKE_WIDTH = "stroke_width"
        const private val INSTANCE_SUFFIX_TEXT_SIZE = "suffix_text_size"
        const private val INSTANCE_SUFFIX_TEXT_PADDING = "suffix_text_padding"
        const private val INSTANCE_BOTTOM_TEXT_SIZE = "bottom_text_size"
        const private val INSTANCE_BOTTOM_TEXT = "bottom_text"
        const private val INSTANCE_TEXT_SIZE = "text_size"
        const private val INSTANCE_TEXT_COLOR = "text_color"
        const private val INSTANCE_PROGRESS = "progress"
        const private val INSTANCE_MAX = "max"
        const private val INSTANCE_FINISHED_STROKE_COLOR = "finished_stroke_color"
        const private val INSTANCE_UNFINISHED_STROKE_COLOR = "unfinished_stroke_color"
        const private val INSTANCE_ARC_ANGLE = "arc_angle"
        const private val INSTANCE_SUFFIX = "suffix"
    }

    lateinit var paint: Paint
    lateinit var textPaint: Paint

    private val rectF = RectF()

    private var strokeWidth: Float = 0f
    private var suffixTextSize: Float = 0f
    private var bottomTextSize: Float = 0f
    private var bottomText: String? = null
    private var textSize: Float = 0f
    private var textColor: Int = 0
    private var progress = 0
    private var max: Int = 0
    private var finishedStrokeColor: Int = 0
    private var unfinishedStrokeColor: Int = 0
    private var arcAngle: Float = 0.toFloat()
    private var suffixText: String = "%"
    private var suffixTextPadding: Float = 0f
    private var bottomHeight: Float = 0f
    private val default_finished_color = Color.WHITE
    private val default_unfinished_color = Color.rgb(72, 106, 176)
    private val default_text_color = Color.rgb(66, 145, 241)
    private val default_suffix_text: String = "%"
    private val default_max = 100
    private val default_arc_angle = 360 * 0.8f

    private val min_size: Int

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr){
        val attributes = context!!.theme.obtainStyledAttributes(attrs, R.styleable.CustomProgress, defStyleAttr, 0)
        initByAttributes(attributes)
        attributes.recycle()
        initPainters()
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        min_size = UiUtils.dp2px(resources, 100f).toInt()
    }

    private fun initByAttributes(attributes: TypedArray) {
        finishedStrokeColor = attributes.getColor(R.styleable.CustomProgress_cp_finished_color, default_finished_color)
        unfinishedStrokeColor = attributes.getColor(R.styleable.CustomProgress_cp_unfinished_color, default_unfinished_color)
        textColor = attributes.getColor(R.styleable.CustomProgress_cp_text_color, default_text_color)
        textSize = attributes.getDimension(R.styleable.CustomProgress_cp_text_size, UiUtils.sp2px(resources, 40f))
        arcAngle = attributes.getDimension(R.styleable.CustomProgress_cp_angle, default_arc_angle)
        setMax(attributes.getInt(R.styleable.CustomProgress_cp_max, default_max))
        setProgress(attributes.getInt(R.styleable.CustomProgress_cp_progress, 0))
        strokeWidth = attributes.getDimension(
                R.styleable.CustomProgress_cp_stroke_width, UiUtils.dp2px(resources, 4f))
        suffixTextSize = attributes.getDimension(R.styleable.CustomProgress_cp_suffix_text_size, UiUtils.sp2px(resources, 15f))
        suffixText = if (TextUtils.isEmpty(attributes.getString(R.styleable.CustomProgress_cp_suffix_text)))
            default_suffix_text
        else
            attributes.getString(R.styleable.CustomProgress_cp_suffix_text)
        suffixTextPadding = attributes.getDimension(R.styleable.CustomProgress_cp_suffix_text_padding, UiUtils.dp2px(resources, 4f))
        bottomTextSize = attributes.getDimension(R.styleable.CustomProgress_cp_bottom_text_size, UiUtils.sp2px(resources, 10f))
        bottomText = attributes.getString(R.styleable.CustomProgress_cp_bottom_text)
    }

    private fun initPainters() {
        textPaint = TextPaint()
        textPaint.color = textColor
        textPaint.textSize = textSize
        textPaint.isAntiAlias = true

        paint = Paint()
        paint.color = default_unfinished_color
        paint.isAntiAlias = true
        paint.strokeWidth = strokeWidth
        paint.style = Paint.Style.STROKE
        paint.strokeCap = Paint.Cap.ROUND
    }

    override fun invalidate() {
        initPainters()
        super.invalidate()
    }

    fun getStrokeWidth(): Float = strokeWidth

    fun setStrokeWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        this.invalidate()
    }

    fun getSuffixTextSize(): Float = suffixTextSize

    fun setSuffixTextSize(suffixTextSize: Float) {
        this.suffixTextSize = suffixTextSize
        this.invalidate()
    }

    fun getBottomText(): String? = bottomText

    fun setBottomText(bottomText: String) {
        this.bottomText = bottomText
        this.invalidate()
    }

    fun getProgress(): Int = progress

    fun setProgress(progress: Int) {
        this.progress = progress
        if (this.progress > getMax()) {
            this.progress %= getMax()
        }
        invalidate()
    }

    fun setProgressWithAnimation(from: Int, to: Int){
        setProgress(from)
        val animation = object: Animation() {
            override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                super.applyTransformation(interpolatedTime, t)
                val value = progress + (to - progress) * interpolatedTime
                setProgress(value.toInt())
            }
        }
        animation.duration = 2000L
        startAnimation(animation)
    }

    fun getMax(): Int = max

    fun setMax(max: Int) {
        if (max > 0) {
            this.max = max
            invalidate()
        }
    }

    fun getBottomTextSize(): Float = bottomTextSize

    fun setBottomTextSize(bottomTextSize: Float) {
        this.bottomTextSize = bottomTextSize
        this.invalidate()
    }

    fun getTextSize(): Float = textSize

    fun setTextSize(textSize: Float) {
        this.textSize = textSize
        this.invalidate()
    }

    fun getTextColor(): Int = textColor

    fun setTextColor(textColor: Int) {
        this.textColor = textColor
        this.invalidate()
    }

    fun getFinishedStrokeColor(): Int = finishedStrokeColor

    fun setFinishedStrokeColor(finishedStrokeColor: Int) {
        this.finishedStrokeColor = finishedStrokeColor
        this.invalidate()
    }

    fun getUnfinishedStrokeColor(): Int = unfinishedStrokeColor

    fun setUnfinishedStrokeColor(unfinishedStrokeColor: Int) {
        this.unfinishedStrokeColor = unfinishedStrokeColor
        this.invalidate()
    }

    fun getArcAngle(): Float = arcAngle

    fun setArcAngle(arcAngle: Float) {
        this.arcAngle = arcAngle
        this.invalidate()
    }

    fun getSuffixText(): String? = suffixText

    fun setSuffixText(suffixText: String) {
        this.suffixText = suffixText
        this.invalidate()
    }

    fun getSuffixTextPadding(): Float = suffixTextPadding

    fun setSuffixTextPadding(suffixTextPadding: Float) {
        this.suffixTextPadding = suffixTextPadding
        this.invalidate()
    }

    override fun getSuggestedMinimumHeight(): Int = min_size

    override fun getSuggestedMinimumWidth(): Int = min_size

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        val size = if (width > height) height else width

        rectF.set(strokeWidth / 2f, strokeWidth / 2f,size - strokeWidth / 2f, size - strokeWidth / 2f)
        val radius = size / 2f
        val angle = (360 - arcAngle) / 2f
        bottomHeight = radius * (1 - Math.cos(angle / 180 * Math.PI)).toFloat()
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val startAngle = 270 - arcAngle / 2f
        val finishedSweepAngle = progress / getMax().toFloat() * arcAngle
        paint.color = unfinishedStrokeColor
        canvas.drawArc(rectF, startAngle, arcAngle, false, paint)
        paint.color = finishedStrokeColor
        canvas.drawArc(rectF, startAngle, finishedSweepAngle, false, paint)

        val text = getProgress().toString()
        if (!TextUtils.isEmpty(text)) {
            textPaint.color = textColor
            textPaint.textSize = textSize
            val textHeight = textPaint.descent() + textPaint.ascent()
            val textBaseline = (height - textHeight) / 2.0f
            canvas.drawText(text, (width - textPaint.measureText(text)) / 2.0f, textBaseline, textPaint)
            textPaint.textSize = suffixTextSize
            val suffixHeight = textPaint.descent() + textPaint.ascent()
            canvas.drawText(suffixText, width / 2.0f + textPaint.measureText(text)
                            + suffixTextPadding, (textBaseline + textHeight - suffixHeight), textPaint)
        }

        if (!TextUtils.isEmpty(getBottomText())) {
            textPaint.textSize = bottomTextSize
            val bottomTextBaseline = (height.toFloat() - bottomHeight - (textPaint.descent() + textPaint.ascent()) / 2)
            canvas.drawText(getBottomText()!!, (width - textPaint.measureText(getBottomText())) / 2.0f, bottomTextBaseline, textPaint)
        }
    }

    override fun onSaveInstanceState(): Parcelable {
        val bundle = Bundle()
        bundle.putParcelable(INSTANCE_STATE, super.onSaveInstanceState())
        bundle.putFloat(INSTANCE_STROKE_WIDTH, getStrokeWidth())
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_SIZE, getSuffixTextSize())
        bundle.putFloat(INSTANCE_SUFFIX_TEXT_PADDING, getSuffixTextPadding())
        bundle.putFloat(INSTANCE_BOTTOM_TEXT_SIZE, getBottomTextSize())
        bundle.putString(INSTANCE_BOTTOM_TEXT, getBottomText())
        bundle.putFloat(INSTANCE_TEXT_SIZE, getTextSize())
        bundle.putInt(INSTANCE_TEXT_COLOR, getTextColor())
        bundle.putInt(INSTANCE_PROGRESS, getProgress())
        bundle.putInt(INSTANCE_MAX, getMax())
        bundle.putInt(INSTANCE_FINISHED_STROKE_COLOR, getFinishedStrokeColor())
        bundle.putInt(INSTANCE_UNFINISHED_STROKE_COLOR,
                getUnfinishedStrokeColor())
        bundle.putFloat(INSTANCE_ARC_ANGLE, getArcAngle())
        bundle.putString(INSTANCE_SUFFIX, getSuffixText())
        return bundle
    }

    override fun onRestoreInstanceState(state: Parcelable) {
        if (state is Bundle) {
            strokeWidth = state.getFloat(INSTANCE_STROKE_WIDTH)
            suffixTextSize = state.getFloat(INSTANCE_SUFFIX_TEXT_SIZE)
            suffixTextPadding = state.getFloat(INSTANCE_SUFFIX_TEXT_PADDING)
            bottomTextSize = state.getFloat(INSTANCE_BOTTOM_TEXT_SIZE)
            bottomText = state.getString(INSTANCE_BOTTOM_TEXT)
            textSize = state.getFloat(INSTANCE_TEXT_SIZE)
            textColor = state.getInt(INSTANCE_TEXT_COLOR)
            setMax(state.getInt(INSTANCE_MAX))
            setProgress(state.getInt(INSTANCE_PROGRESS))
            finishedStrokeColor = state.getInt(INSTANCE_FINISHED_STROKE_COLOR)
            unfinishedStrokeColor = state
                    .getInt(INSTANCE_UNFINISHED_STROKE_COLOR)
            suffixText = state.getString(INSTANCE_SUFFIX)
            initPainters()
            super.onRestoreInstanceState(state.getParcelable(INSTANCE_STATE))
        } else {
            super.onRestoreInstanceState(state)
        }
    }


}