package com.chencc.androidstudynotescode.customview.viewpager

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager

/**
 *  ×Ô¶¨Òå viewPager Á·Ï°
 */
class MyViewPager : ViewPager{
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var height = 0

        for (i in 0 until childCount) {
            val child = getChildAt(i)
            val param = child.layoutParams
            val childWidthSpec = ViewGroup.getChildMeasureSpec(widthMeasureSpec,0, param.width)
            val childHeightSpec = ViewGroup.getChildMeasureSpec(heightMeasureSpec,0, param.height)
            child.measure(childWidthSpec, childHeightSpec)

            val h = child.measuredHeight
            if (h > height){
                height = h
            }
        }

        var heightSpec = MeasureSpec.makeMeasureSpec(height, MeasureSpec.EXACTLY)
        setMeasuredDimension(widthMeasureSpec, heightSpec)
        super.onMeasure(widthMeasureSpec, heightSpec)
    }
}