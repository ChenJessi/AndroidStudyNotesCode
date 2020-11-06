package com.chencc.androidstudynotescode.materialdesign.viewpager2.behavior

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.marginTop
import com.google.android.material.appbar.AppBarLayout

class BottomNavBehavior : CoordinatorLayout.Behavior<View> {
    constructor() : super()
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)


    override fun onLayoutChild(parent: CoordinatorLayout, child: View, layoutDirection: Int): Boolean {
        (child.layoutParams as CoordinatorLayout.LayoutParams).topMargin = parent.measuredHeight - child.measuredHeight
        return super.onLayoutChild(parent, child, layoutDirection)
    }

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is AppBarLayout
    }

    /**
     * 被依赖布局的变化  即 AppBarLayout
     */
    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        /**
         * topAndBottomOffset 为  AppBarLayout 的偏移量
         * 根据 AppBarLayout 的 纵轴偏移量 移动 child 即当前 BottomNavigationView
         */
        val topAndBottomOffset =
            ((dependency.layoutParams as CoordinatorLayout.LayoutParams).behavior as AppBarLayout.Behavior).topAndBottomOffset
        Log.e("TAG", "onDependentViewChanged:    $topAndBottomOffset" )
        child.translationY =  (-topAndBottomOffset).toFloat()
        return false
    }
}