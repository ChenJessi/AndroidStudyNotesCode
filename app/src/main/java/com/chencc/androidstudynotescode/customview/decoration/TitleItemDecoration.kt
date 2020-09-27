package com.chencc.androidstudynotescode.customview.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.utils.dp2px

/**
 * 悬浮标题 ItemDecoration
 */
private const val TAG = "TitleItemDecoration"
class TitleItemDecoration : RecyclerView.ItemDecoration() {

    val groupHeaderHeight = 30f.dp2px
    val decorationHeight = 8f.dp2px

    val mPaint : Paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.RED
    }
    val rect = Rect()


    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        if (parent.adapter is DecorationAdapter){
            val adapter = parent.adapter as DecorationAdapter
            val left = parent.paddingLeft
            val right = parent.width - parent.paddingRight
            // 获取当前可见 child
            val count = parent.childCount
            for (index in 0 until count){
                val itemView = parent.getChildAt(index)
                val position = parent.getChildAdapterPosition(itemView)
                val isHead = adapter.isHeadTitle(position)
                if (isHead){
                    rect.set(left, (itemView.top - groupHeaderHeight).toInt(), right, itemView.top)
                    c.drawRect(rect, mPaint)
                }else {
                    rect.set(left, (itemView.top - decorationHeight).toInt(), right, itemView.top)
                    c.drawRect(rect, mPaint)
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent.adapter is DecorationAdapter){
            val adapter = parent.adapter as DecorationAdapter

            val position = parent.getChildAdapterPosition(view)
            val isHead = adapter.isHeadTitle(position)
            if (isHead){
                outRect.set(0, groupHeaderHeight.toInt(), 0, 0)
            }else{
                outRect.set(0, decorationHeight.toInt() , 0, 0)
            }
        }
    }
}