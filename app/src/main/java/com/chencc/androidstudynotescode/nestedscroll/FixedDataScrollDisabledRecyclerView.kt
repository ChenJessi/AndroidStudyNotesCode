package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.R

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class FixedDataScrollDisabledRecyclerView: RecyclerView {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )


    init {
        layoutManager = LinearLayoutManager(context)
        adapter = RecyclerAdapter(initList())

    }

    override fun onTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    override fun onInterceptTouchEvent(e: MotionEvent?): Boolean {
        return false
    }

    private fun initList() : MutableList<String>{
        return mutableListOf<String>().apply {
            add("ParentView item 0")
            add("ParentView item 1")
            add("ParentView item 2")
        }
    }
}