package com.chencc.androidstudynotescode.customview.flowLayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec
import android.view.ViewGroup
import kotlin.math.max

/**
 * 流式布局
 */
class FlowLayout : ViewGroup {
    private  val TAG = "FlowLayout"

    private val allLines = mutableListOf<MutableList<View>>()           // 记录每一行的 View
    private val linesHeight = mutableListOf<Int>()           // 记录每一行的 View



    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allLines.clear()
        linesHeight.clear()


        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)     // 父控件给的高度
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)       // 父控件给的宽度

        var parentNeededWidth = 0               // 子view要求的宽度
        var parentNeededHeight = 0              // // 子view要求的高度
        var lineView = mutableListOf<View>()

        var lineHeight = 0  // 行高
        var lineWidthUse = 0    // 已经被子View 使用的宽度

        for (i in 0 until childCount){
            val childView = getChildAt(i)
            val lp = childView.layoutParams

            if (childView.visibility != GONE){
                var childWidthSpec = getChildMeasureSpec(widthMeasureSpec,paddingLeft + paddingRight, lp.width)
                var childHeightSpec = getChildMeasureSpec(heightMeasureSpec,paddingTop + paddingBottom, lp.height)
                childView.measure(childWidthSpec, childHeightSpec)
                var height = childView.measuredHeight
                var width = childView.measuredWidth

                lineHeight = max(height, lineHeight)        // 每行高度最大的 子view 为行高

                /**
                 * 是否需要换行
                 */
                if (width > selfWidth - lineWidthUse){  //    剩余宽度不够了就换行
                    allLines.add(lineView)
                    lineView = mutableListOf()
                    linesHeight.add(lineHeight)

                    parentNeededWidth = max(parentNeededWidth, lineWidthUse)
                    parentNeededHeight += lineHeight
                    lineHeight = 0
                    lineWidthUse = 0
                }

                lineWidthUse += width
                lineView.add(childView)

                /**
                 * 处理最后一行
                 */
                if (i == childCount -1){
                    allLines.add(lineView)
                    linesHeight.add(lineHeight)
                    parentNeededWidth = max(parentNeededWidth, lineWidthUse)
                    parentNeededHeight += lineHeight
                }

            }
        }
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeededWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeededHeight

        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var heightUse = 0
        for (i in 0 until linesHeight.size){
            val lineViews = allLines[i]
            var widthUse = 0
            for (j in  lineViews){
                j.layout(widthUse,heightUse, widthUse + j.measuredWidth, heightUse + j.measuredHeight)
                widthUse += j.measuredWidth
            }
            heightUse += linesHeight[i]
        }
    }
}