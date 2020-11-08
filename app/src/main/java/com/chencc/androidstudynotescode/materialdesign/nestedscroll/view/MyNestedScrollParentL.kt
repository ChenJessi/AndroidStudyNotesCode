package com.chencc.androidstudynotescode.materialdesign.nestedscroll.view

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.core.view.NestedScrollingParent
import androidx.core.view.NestedScrollingParentHelper

/**
 * @author Created by CHEN on 2020/11/8
 * @email 188669@163.com
 */

private const val TAG = "MyNestedScrollParentL"

class MyNestedScrollParentL : LinearLayout, NestedScrollingParent{

    private var helper = NestedScrollingParentHelper(this)

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)


    init {

    }
    var realHeight = 0
    var heightSpec = 0
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        realHeight = 0
        heightSpec = heightMeasureSpec
        for (i in 0 until childCount){
            val view = getChildAt(i)
            heightSpec = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(heightMeasureSpec) , MeasureSpec.UNSPECIFIED)
            
        }
    }


}