package com.android.columnchart

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import java.util.*
import kotlin.math.roundToInt


class AZColumnChart : View {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    private var shadowColor: Int = Color.parseColor("#57000000")
    private var textPaint = Paint().apply {
        textSize = 29f
        color = Color.BLACK
        isAntiAlias = true
        flags = Paint.ANTI_ALIAS_FLAG
    }

    private var largestText = ""
    private var customTexts: List<String>? = null

    private var topLeftCorner = 5f
    private var topRightCorner = 5f
    private var bottomLeftCorner = 0f
    private var bottomRightCorner = 0f

    private var marginIcon = 0f

    private var isShowIcon = GONE
    private var topColorOpacity = 65f
    private var bottomColorOpacity = 100f



    private var colorMode: ColorMode = ColorMode.AUTO_GENERATE_GRADIENT_COLOR
    private var inputColor: InputColor = InputColor.DEFAULT_GRADIENT_COLOR

    enum class InputColor {
        CUSTOM_COLOR, DEFAULT_SOLID_COLOR, DEFAULT_GRADIENT_COLOR
    }

    enum class ColorMode {
        SOLID_COLOR, GRADIENT_COLOR, AUTO_GENERATE_GRADIENT_COLOR
    }



    @ColorInt
    private var chartBackgroundColor = Color.TRANSPARENT
    private var iconTint: Int? = null

    private var listEntry = ArrayList<ChartEntry>()

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas!!.drawPaint(Paint().apply {
            color = chartBackgroundColor
        })

        /* If it was empty, the default information */
        if (listEntry.size == 0) {
            listEntry.addAll(
                arrayListOf(
                    ChartEntry(
                        value = 120f,
                        color = Color.RED
                    ),
                    ChartEntry(
                        value = 140f,
                        color = Color.BLACK
                    ),
                    ChartEntry(
                        value = 180f,
                        color = Color.GREEN
                    ),
                    ChartEntry(
                        value = 200f,
                        color = Color.YELLOW
                    ),
                    ChartEntry(
                        value = 340f,
                        color = Color.BLUE
                    ),
                    ChartEntry(
                        value = 280f,
                        color = Color.DKGRAY
                    )

                )
            )
        }
        listEntry.sortBy { it.value }
        val minimumEntry = listEntry[0].value
        val maximumEntry = listEntry[listEntry.size - 1].value

        if (customTexts == null) {
            val texts = arrayOf(
                minimumEntry,
                ((((((maximumEntry - minimumEntry) / 2) + minimumEntry) - minimumEntry) / 2) + minimumEntry),
                (((maximumEntry - minimumEntry) / 2) + minimumEntry),
                (((maximumEntry - (((maximumEntry - minimumEntry) / 2) + minimumEntry)) / 2) + (((maximumEntry - minimumEntry) / 2) + minimumEntry)),
                maximumEntry
            )
            texts.apply {
                sort()
                reverse()
                forEachIndexed { index, value ->
                    canvas.drawText(
                        value.roundToInt().toString(),
                        width.percentage(5f),
                        ((height / 6) * (index + 1)).toFloat(),
                        textPaint
                    )
                }
                largestText = findMostFrequentNumber(this.floatArrayToIntArray()).toString()
            }
        } else {
            customTexts!!.forEachIndexed { index, value ->
                canvas.drawText(
                    value,
                    width.percentage(5f),
                    ((height / 6) * (index + 1)).toFloat(),
                    textPaint
                )
            }
            largestText = findLargestTextLength(customTexts)
        }


        val textRect = Rect()
        textPaint.getTextBounds(largestText, 0, largestText.length, textRect)
        var marginLeft = width.percentage(6f)
        var marginRight = width.percentage(2f)

        val widthForColumns =
            (width.toFloat() - marginRight) - (((width.percentage(5f) + textRect.width())) + marginLeft)
        val widthLeftText = width - widthForColumns
        val columnWidth = widthForColumns / listEntry.size
        val marginTopColumn = height.percentage(4f)
        val marginBottomColumn = height.percentage(4f)



        listEntry.apply {
            sortBy { it.value }
            reverse()
            forEachIndexed { index, chartEntry ->
                val corners = floatArrayOf(
                    topLeftCorner, topLeftCorner,
                    topRightCorner, topRightCorner,
                    bottomRightCorner, bottomRightCorner,
                    bottomLeftCorner, bottomLeftCorner
                )

                val heightColumn = ((chartEntry.value * 100 / maximumEntry) * height) / 100
                val rect = RectF(
                    widthLeftText + (columnWidth * index),
                    (height - heightColumn)+marginTopColumn,
                    widthLeftText + (columnWidth * (index + 1)),
                    height - marginBottomColumn
                )
                val path = Path()
                val rectPaint = Paint()

                when (inputColor) {
                    InputColor.CUSTOM_COLOR -> {
                        when (colorMode) {
                            ColorMode.GRADIENT_COLOR -> {
                                if (chartEntry.startColor != null && chartEntry.endColor != null) {
                                    val rectGradient = LinearGradient(
                                        rect.centerX(),
                                        rect.top,
                                        rect.centerX(),
                                        rect.bottom,
                                        chartEntry.startColor,
                                        chartEntry.endColor,
                                        Shader.TileMode.CLAMP
                                    )
                                    rectPaint.shader = rectGradient
                                } else {
                                    val rectGradient = LinearGradient(
                                        rect.centerX(),
                                        rect.top,
                                        rect.centerX(),
                                        rect.bottom,
                                        gradientColor(index).second,
                                        gradientColor(index).first,
                                        Shader.TileMode.CLAMP
                                    )
                                    rectPaint.shader = rectGradient
                                }

                            }
                            ColorMode.SOLID_COLOR -> {
                                if (chartEntry.color != null)
                                    rectPaint.color = chartEntry.color
                                else
                                    rectPaint.color = getDefaultSolidColor(index);
                            }
                            ColorMode.AUTO_GENERATE_GRADIENT_COLOR -> {
                                if (chartEntry.color!=null){
                                    val rectGradient = LinearGradient(
                                        rect.centerX(),
                                        rect.top,
                                        rect.centerX(),
                                        rect.bottom,
                                        adjustAlpha(chartEntry.color, topColorOpacity),
                                        adjustAlpha(chartEntry.color, bottomColorOpacity),
                                        Shader.TileMode.CLAMP
                                    )
                                    rectPaint.shader = rectGradient
                                }else{
                                    val rectGradient = LinearGradient(
                                        rect.centerX(),
                                        rect.top,
                                        rect.centerX(),
                                        rect.bottom,
                                        gradientColor(index).second,
                                        gradientColor(index).first,
                                        Shader.TileMode.CLAMP
                                    )
                                    rectPaint.shader = rectGradient

                                }

                            }
                        }
                    }

                    InputColor.DEFAULT_SOLID_COLOR -> {
                        rectPaint.color = getDefaultSolidColor(index);
                    }

                    InputColor.DEFAULT_GRADIENT_COLOR -> {
                        val rectGradient = LinearGradient(
                            rect.centerX(),
                            rect.top,
                            rect.centerX(),
                            rect.bottom,
                            gradientColor(index).second,
                            gradientColor(index).first,
                            Shader.TileMode.CLAMP
                        )
                        rectPaint.shader = rectGradient
                    }
                }
                path.addRoundRect(rect, corners, Path.Direction.CW)
                canvas.drawPath(
                    path, rectPaint
                )
                if (isShowIcon == VISIBLE) {
                    val d = resources.getDrawable(chartEntry.icon!!, null)
                    val marginImage = (rect.right - rect.left).percentage(20f)
                    canvas.drawBitmap(
                        drawableToBitmap(d),
                        null,
                        Rect(
                            (((((rect.left)) + marginImage))+marginIcon).toInt(),
                            ((((rect.bottom - (rect.right - rect.left)).toInt()) + marginImage)+marginIcon).toInt(),
                            ((((rect.right).toInt()) - marginImage)-marginIcon).toInt(),
                            ((rect.bottom - marginImage)-marginIcon).toInt()
                        ),
                        Paint().apply {
                            if (iconTint!=null)
                                colorFilter = PorterDuffColorFilter(iconTint!!, PorterDuff.Mode.SRC_IN)
                        }
                    )
                }
                if (index > 0) {
                    val rectShadow = RectF(
                        rect.left,
                        rect.top,
                        ((rect.right - rect.left) / 3) + rect.left,
                        rect.bottom
                    )
                    val shadow = LinearGradient(
                        rect.left - 50f,
                        rect.centerY(),
                        rectShadow.right,
                        rectShadow.centerY(),
                        Color.parseColor("#57000000"),
                        Color.parseColor("#00000000"),
                        Shader.TileMode.CLAMP
                    )
                    canvas.drawRect(rectShadow, Paint().apply {
                        shader = shadow
                    })

                }
            }
        }


    }

    private fun gradientColor(index: Int): Pair<Int, Int> {
        //first value -> Bold Color
        //second value -> dim color
        var colors = arrayOf(
            Pair(Color.parseColor("#0D9AC6"), Color.parseColor("#4BC2D2")),
            Pair(Color.parseColor("#126EAB"), Color.parseColor("#2698BD")),
            Pair(Color.parseColor("#8DE9DC"), Color.parseColor("#A6F2CC")),
            Pair(Color.parseColor("#48A79F"), Color.parseColor("#5BB98A")),
            Pair(Color.parseColor("#F29D73"), Color.parseColor("#FFAE87")),
            Pair(Color.parseColor("#0D9AC6"), Color.parseColor("#4BC2D2")),
            Pair(Color.parseColor("#38b000"), Color.parseColor("#70e000")),
            Pair(Color.parseColor("#7D8F69"), Color.parseColor("#A9AF7E")),
            Pair(Color.parseColor("#C689C6"), Color.parseColor("#FFABE1")),
            Pair(Color.parseColor("#42855B"), Color.parseColor("#90B77D")),
        )
        return colors[(index % colors.size)]
    }

    private fun getDefaultSolidColor(index: Int): Int {
        val colors = arrayOf(
            Color.parseColor("#33d9b2"),
            Color.parseColor("#34ace0"),
            Color.parseColor("#706fd3"),
            Color.parseColor("#f7f1e3"),
            Color.parseColor("#40407a"),
            Color.parseColor("#ccae62"),
            Color.parseColor("#cc8e35"),
            Color.parseColor("#84817a"),
            Color.parseColor("#cd6133"),
            Color.parseColor("#b33939"),
            Color.parseColor("#ffda79"),
            Color.parseColor("#ffb142"),
            Color.parseColor("#d1ccc0"),
            Color.parseColor("#ff793f"),
            Color.parseColor("#ff5252"),
            Color.parseColor("#218c74"),
            Color.parseColor("#227093"),
            Color.parseColor("#aaa69d"),
            Color.parseColor("#474787"),
            Color.parseColor("#2c2c54")
        )
        return colors[index % colors.size];

    }


    fun setData(list: ArrayList<ChartEntry>) {
        listEntry.clear()
        listEntry.addAll(list)

    }

    fun setTextColor(@ColorInt color: Int) {
        textPaint.color = color

    }

    fun getTextColor(): Int {
        return textPaint.color
    }

    fun getRandomColor(): Int {
        val rnd = Random()
        return Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256))
    }

    fun setTypeface(typeface: Typeface) {
        textPaint.typeface = typeface

    }

    fun setTextSize(textSize: Float) {
        textPaint.textSize = textSize

    }

    fun getTextSize(): Float {
        return textPaint.textSize
    }

    fun setTextPaint(textPaint: Paint) {
        this.textPaint = textPaint

    }

    fun getTextPaint(): Paint {
        return textPaint
    }

    fun setChartBackgroundColor(@ColorInt color: Int) {
        this.chartBackgroundColor = color

    }


    fun setCornerColumn(topLeft: Float, topRight: Float, bottomLeft: Float, bottomRight: Float) {
        this.topLeftCorner = topLeft
        this.topRightCorner = topRight
        this.bottomRightCorner = bottomRight
        this.bottomLeftCorner = bottomLeft
    }

    fun setColorMode(mode: ColorMode) {
        this.colorMode = mode

    }

    fun getColorMode(): ColorMode {
        return colorMode
    }

    fun setIconVisibility(visibility: Int) {
        isShowIcon = visibility

    }

    fun getIconVisibility(): Int {
        return isShowIcon
    }

    fun setShadowColor(color: Int) {
        this.shadowColor = color

    }

    fun getShadowColor(): Int {
        return shadowColor
    }

    fun setTopColorOpacity(opacity: Float) {
        topColorOpacity = opacity
    }

    fun getTopColorOpacity(): Float {
        return topColorOpacity
    }

    fun setBottomColorOpacity(opacity: Float) {
        bottomColorOpacity = opacity
    }

    fun getBottomColorOpacity(): Float {
        return bottomColorOpacity
    }

    fun build() {
        invalidate()
    }

    fun setIconTint(color: Int){
        iconTint=color
    }

    fun getIconTint(): Int? {
        return iconTint
    }

    fun setMarginIcon(margin:Float){
        marginIcon=margin
    }

    fun getMarginIcon(): Float {
        return marginIcon
    }

    fun setInputColor(inputColor: InputColor){
        this.inputColor=inputColor
    }

    fun getInputColor(): InputColor {
        return this.inputColor
    }

}