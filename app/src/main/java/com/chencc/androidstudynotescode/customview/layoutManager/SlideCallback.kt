package com.chencc.androidstudynotescode.customview.layoutManager

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.sqrt


class SlideCallback(val adapter: CardRecyclerAdapter, val mList :MutableList<String>) : ItemTouchHelper.SimpleCallback(0, 15) {

    // 拖拽
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    // 滑动
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val removeAt = mList.removeAt(viewHolder.layoutPosition)
        mList.add(removeAt)
        adapter.notifyDataSetChanged()
    }

//    override fun hasSwipeFlag(
//        recyclerView: RecyclerView?,
//        viewHolder: RecyclerView.ViewHolder?
//    ): Boolean {
//
//    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
        // 以 recyclerView 宽度的一半 为准  大于则划出   小于则回到初始位置
        // 已经拖动距离 和 最大拖动距离的比值
        val maxDistance = recyclerView.width * 0.5f
        val distance = sqrt(dX * dX + dY * dY)
        val fraction = if(distance / maxDistance > 1) 1f else distance / maxDistance

        // 显示的item最多为4个
        val itemCount = recyclerView.childCount


//        for (i in itemCount - 2 downTo  0){
//            val view = recyclerView.getChildAt(i)
//
//            val level = itemCount - 1 - i
//
//            if (level > 0  && level < MAX_SHOW_COUNT -1){
//                view.translationY = ((TRANS_Y_GAP * level) - (fraction * TRANS_Y_GAP))
//                view.scaleX = (1 - SCALE_GAP * level) + (fraction * SCALE_GAP)
//                view.scaleY = (1 - SCALE_GAP * level) + (fraction * SCALE_GAP)
//            }
//        }

        for (i in 0 until itemCount){
            val view = recyclerView.getChildAt(i)

            val level = itemCount - i - 1
            if (level > 0 && level < MAX_SHOW_COUNT -1){
                view.translationY = ((TRANS_Y_GAP * level) - (fraction * TRANS_Y_GAP))
                view.scaleX = (1 - SCALE_GAP * level) + (fraction * SCALE_GAP)
                view.scaleY = (1 - SCALE_GAP * level) + (fraction * SCALE_GAP)
            }

        }
        

    }

    override fun getAnimationDuration(recyclerView: RecyclerView, animationType: Int, animateDx: Float, animateDy: Float): Long {
        return 800
    }
}