package com.chencc.androidstudynotescode.customview.layoutManager

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.utils.dp2px

var MAX_SHOW_COUNT = 4
var TRANS_Y_GAP = 15f.dp2px
var SCALE_GAP = 0.05f

class SlideCardLayoutManager : RecyclerView.LayoutManager() {


    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }


    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {

        // ViewHolder 回收复用
        detachAndScrapAttachedViews(recycler!!)
        val itemCount = itemCount

        val bottomPosition = if (itemCount <= MAX_SHOW_COUNT){
//            0
            itemCount
        }else{
//            itemCount - MAX_SHOW_COUNT
            MAX_SHOW_COUNT
        }

//        for (i in bottomPosition until itemCount){
//            // 复用
//            val view = recycler.getViewForPosition(i)
//            addView(view)
//
//            measureChildWithMargins(view, 0, 0)
//
//            val widthSpace = width - getDecoratedMeasuredWidth(view)
//            val heightSpace = height - getDecoratedMeasuredHeight(view)
//
//            // 布局
//            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view))
//
//            val level = itemCount - i - 1
//            if (level > 0){
//                if (level < MAX_SHOW_COUNT - 1){
//                    view.translationY = TRANS_Y_GAP * level
//                    view.scaleX = (1 - SCALE_GAP * level)
//                    view.scaleY = (1 - SCALE_GAP * level)
//                }else{
//                    view.translationY = TRANS_Y_GAP * (level - 1)
//                    view.scaleX = (1 - SCALE_GAP * (level - 1))
//                    view.scaleY = (1 - SCALE_GAP * (level - 1))
//                }
//
//            }
//        }
        // 根据item 正序显示
        for (i in bottomPosition - 1 downTo  0){
            // 复用
            val view = recycler.getViewForPosition(i)
            addView(view)

            measureChildWithMargins(view, 0, 0)

            val widthSpace = width - getDecoratedMeasuredWidth(view)
            val heightSpace = height - getDecoratedMeasuredHeight(view)

            // 布局
            layoutDecoratedWithMargins(view, widthSpace / 2, heightSpace / 2, widthSpace / 2 + getDecoratedMeasuredWidth(view), heightSpace / 2 + getDecoratedMeasuredHeight(view))

            val level =   i
            if (i == MAX_SHOW_COUNT -1){
                view.translationY = (TRANS_Y_GAP * (level - 1))
                view.scaleX = (1 - SCALE_GAP * (level - 1))
                view.scaleY = (1 - SCALE_GAP * (level - 1))
            }else{
                view.translationY = (TRANS_Y_GAP * level)
                view.scaleX = (1 - SCALE_GAP * level)
                view.scaleY = (1 - SCALE_GAP * level)
            }

        }

    }

}