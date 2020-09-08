package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.core.widget.NestedScrollView
import com.chencc.androidstudynotescode.utils.FlingHelper

/**
 * tab 吸顶的 nestedScrollLayout
 */
class NestedScrollLayout : NestedScrollView{

    private val flingHelper by lazy { FlingHelper(context) }
    // 用于判断 recyclerView 是否在 fling
    private var isStartFling = false

    private  val TAG = "NestedScrollLayout"
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    private fun init(){

        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isStartFling){

            }
        }
    }
}