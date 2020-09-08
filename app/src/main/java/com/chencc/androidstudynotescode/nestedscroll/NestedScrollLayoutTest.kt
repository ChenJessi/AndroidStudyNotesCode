package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView


class NestedScrollLayoutTest : NestedScrollView {
    private var contentView: ViewGroup? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )



    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = (getChildAt(0) as ViewGroup?)?.getChildAt(1) as ViewGroup?
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // 调整contentView的高度为父容器高度，使之填充布局，避免父容器滚动后出现空白
        // 将 tabLayout + viewPager 高度设置为父布局高度，也就是屏幕高度
        val lp = contentView?.layoutParams
        lp?.height = measuredHeight
        contentView?.layoutParams = lp
    }
}