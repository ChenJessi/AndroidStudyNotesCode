package com.chencc.androidstudynotescode.customview.decoration

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.utils.dp2px
import com.chencc.androidstudynotescode.utils.sp2px
import kotlin.math.max
import kotlin.math.min

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
    val mTextPaint = Paint().apply{
        textSize = 18f.sp2px
        color = Color.WHITE
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    val rect = Rect()
    val fontMetrics = Paint.FontMetrics()

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)
        if(parent.adapter is DecorationAdapter){
            val adapter = parent.adapter as DecorationAdapter
            val left = parent.paddingLeft
            val top = parent.paddingTop
            val right = parent.width - parent.paddingRight
            val position = (parent.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            val view = parent.findViewHolderForAdapterPosition(position)!!.itemView
            val isHead = adapter.isHeadTitle(position + 1)
            if (isHead){
                //  rectBottom 是顶部悬浮title 展示出来的高度
                val rectBottom = min((view.bottom - top).toFloat()  ,  groupHeaderHeight)
                rect.set(left, top, right, top + rectBottom.toInt())

                val title = adapter.mList[position].title
                mTextPaint.getFontMetrics(fontMetrics)
                val baseLine = top + rectBottom - groupHeaderHeight / 2 - fontMetrics.ascent / 2 - fontMetrics.descent / 2
                c.clipRect(rect)
                c.getClipBounds(rect)
                c.drawRect(rect, mPaint)
                c.drawText(title, 0, title.length, left.toFloat(), baseLine, mTextPaint)

            }else{
                rect.set(left, top, right, (top + groupHeaderHeight).toInt())
                c.drawRect(rect, mPaint)
                val title = adapter.mList[position].title
                mTextPaint.getFontMetrics(fontMetrics)
                val baseLine = top +  groupHeaderHeight / 2 - fontMetrics.ascent / 2 - fontMetrics.descent / 2
                c.drawText(title, 0, title.length, left.toFloat(), baseLine, mTextPaint)
            }
        }
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
                val view = parent.getChildAt(index)
                val position = parent.getChildAdapterPosition(view)
                val isHead = adapter.isHeadTitle(position)
                val lastPosition = (parent.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()

                val lastView = parent.findViewHolderForAdapterPosition(lastPosition + 1)?.itemView
                Log.e(TAG, "onDraw:  lastPosition  :    $lastPosition  ${lastView?.bottom}     ${parent.height}    ${parent.paddingBottom}")
                if (view.top - groupHeaderHeight < parent.top ){
                    continue
                }

                if (isHead ){
                    if (position == lastPosition + 1){
                        continue
                    }else {
                        val bottom = min(view.top, parent.height - parent.paddingBottom)
                        val rectTop = max(view.top - groupHeaderHeight.toInt(), parent.paddingTop)
                        rect.set(left, rectTop, right, bottom)
                        val title = adapter.mList[position].title
                        mTextPaint.getFontMetrics(fontMetrics)
                        val baseLine = view.top - groupHeaderHeight / 2  - fontMetrics.ascent / 2 - fontMetrics.descent / 2
                        c.clipRect(parent.paddingLeft, parent.paddingTop, parent.width - parent.paddingRight, parent.height - parent.paddingBottom )
                        c.getClipBounds(rect)
                        c.drawRect(rect, mPaint)
                        c.drawText(title, 0, title.length, left.toFloat(), baseLine, mTextPaint)
                    }

                } else {
                    if (position == lastPosition + 1){
                        continue
                    }else {
                        val bottom = min(view.top, parent.height - parent.paddingBottom)
                        rect.set(left, (view.top - decorationHeight).toInt(), right, bottom)
                        c.drawRect(rect, mPaint)
                    }
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