package com.chencc.androidstudynotescode.materialdesign.coordinator.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.chencc.androidstudynotescode.materialdesign.coordinator.view.DependedView

/**
 *  Behavior
 * 跟随行为
 */
class BrotherFollowBehavior(var mContext : Context, var attrs : AttributeSet) : CoordinatorLayout.Behavior<View>(mContext, attrs) {

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is DependedView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        child.x = dependency.x
        child.y = dependency.bottom + 50f
        return true
    }

}