package com.chencc.androidstudynotescode.materialdesign.constraintlayout.helper

import android.content.Context
import android.util.AttributeSet
import android.view.ViewAnimationUtils
import androidx.constraintlayout.widget.ConstraintHelper
import androidx.constraintlayout.widget.ConstraintLayout


class CircularRevealHelper(mContext : Context, attrs : AttributeSet) : ConstraintHelper(mContext, attrs){

    override fun updatePostLayout(container: ConstraintLayout?) {
        super.updatePostLayout(container)
        getViews(container).forEach {
            val animator = ViewAnimationUtils.createCircularReveal(
                it, it.measuredWidth / 2, it.measuredHeight / 2, 50f, it.measuredWidth.toFloat()
            )
            animator.duration = 2000
            animator.start()
        }
    }

    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)
    }
}