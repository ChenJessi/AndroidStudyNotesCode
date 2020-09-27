package com.chencc.androidstudynotescode.customview.flowLayout

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.skin.SkinAttribute
import com.chencc.androidstudynotescode.utils.dp2px
import com.chencc.androidstudynotescode.utils.getResId
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.max

/**
 * 流式布局
 */
class FlowLayout : ViewGroup {
    private  val TAG = "FlowLayout"

    private val allLines = mutableListOf<MutableList<View>>()           // 记录每一行的 View
    private val linesHeight = mutableListOf<Int>()           // 记录每一行的 View

    private var mHorizontalSpacing: Int = 10f.dp2px.toInt() //每个item横向间距

    private var mVerticalSpacing: Int = 8f.dp2px.toInt() //每个item纵向间距


    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs){
    }
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )
    init {
//        for (i in 0..9){
//            val view = TextView(context).apply {
//                text = "测试text1 ${1}"
//                background = ContextCompat.getDrawable(context, R.drawable.shape_button_circular)
//            }
//            addView(view)
//        }
    }
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        allLines.clear()
        linesHeight.clear()


        val selfHeight = MeasureSpec.getSize(heightMeasureSpec)     // 父控件给的高度
        val selfWidth = MeasureSpec.getSize(widthMeasureSpec)       // 父控件给的宽度

        var parentNeededWidth = 0               // 子view要求的宽度
        var parentNeededHeight = paddingTop + paddingBottom              // // 子view要求的高度
        var lineView = mutableListOf<View>()

        var lineHeight = 0  // 行高
        var lineWidthUse = paddingLeft    // 已经被子View 使用的宽度

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
                if (width > selfWidth - lineWidthUse - paddingRight){  //    剩余宽度不够了就换行
                    allLines.add(lineView)
                    lineView = mutableListOf()
                    linesHeight.add(lineHeight)
                    // 每行最后一个view 多增加了一个 mHorizontalSpacing   换行时候要减去
                    parentNeededWidth = max(parentNeededWidth, lineWidthUse + paddingRight - mHorizontalSpacing)
                    // 每次换行 多加一个 行间距  最后一行不用
                    parentNeededHeight += if (i == childCount -1){
                        lineHeight
                    }else{
                        lineHeight + mVerticalSpacing
                    }

                    lineHeight = 0
                    lineWidthUse = paddingLeft
                }
                // 使用过的宽度每次增加  view 宽度 + 间距
                lineWidthUse += (width + mHorizontalSpacing)
                lineView.add(childView)

                /**
                 * 处理最后一行
                 */
                if (i == childCount -1){
                    allLines.add(lineView)
                    linesHeight.add(lineHeight)
                    parentNeededWidth = max(parentNeededWidth, lineWidthUse  + paddingRight - mHorizontalSpacing)
                    parentNeededHeight += lineHeight
                }

            }
        }
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)

        // 如果尺寸是精确模式 就使用将确定的大小  如  "layout_width" = 100dp  那测量大小就是 100dp
        // 否则就使用 实际计算出来的尺寸
        val realWidth = if (widthMode == MeasureSpec.EXACTLY) selfWidth else parentNeededWidth
        val realHeight = if (heightMode == MeasureSpec.EXACTLY) selfHeight else parentNeededHeight

        setMeasuredDimension(realWidth, realHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        var heightUse = paddingTop
        for (i in 0 until linesHeight.size){
            val lineViews = allLines[i]
            var widthUse = paddingLeft
            for (j in  lineViews){
                j.layout(widthUse,heightUse, widthUse + j.measuredWidth, heightUse + j.measuredHeight)
                widthUse += (j.measuredWidth + mHorizontalSpacing)
            }
            heightUse += (linesHeight[i] + mVerticalSpacing)
        }
    }
}



