package com.chencc.androidstudynotescode.materialdesign.coordinator.behavior

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.chencc.androidstudynotescode.materialdesign.coordinator.view.DependedView

/**
 * Behavior
 *  变色
 */
class BrotherChameleonBehavior (var mContext : Context, var attrs : AttributeSet) : CoordinatorLayout.Behavior<View>(mContext, attrs) {

    private val mArgbEvaluator = ArgbEvaluator()

    override fun layoutDependsOn(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        return dependency is DependedView
    }

    override fun onDependentViewChanged(parent: CoordinatorLayout, child: View, dependency: View): Boolean {
        var color = mArgbEvaluator.evaluate(dependency.y / parent.height, Color.WHITE, Color.BLACK) as Int
        child.setBackgroundColor(color)
        return true
    }
}