package com.chencc.androidstudynotescode.materialdesign.coordinator.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView

/**
 * recyclerView 滚动
 */
class ScrollerBehavior(var mContext : Context, var attrs : AttributeSet) : CoordinatorLayout.Behavior<RecyclerView>(mContext, attrs) {


    /**
     * 表示是否给应用了Behavior 的View 指定一个依赖的布局
     *
     */
    override fun layoutDependsOn(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        return dependency is TextView
    }

    /**
     * 依赖的View发生变化的时候回掉的方法
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: RecyclerView, dependency: View): Boolean {
        ViewCompat.offsetTopAndBottom(child, (dependency.bottom - child.top));
        return false
    }

}