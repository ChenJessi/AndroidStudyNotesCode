package com.chencc.androidstudynotescode.customview.viewpager

import android.util.Log
import android.view.View
import androidx.viewpager.widget.ViewPager

class PageTransform : ViewPager.PageTransformer{

    private val DEFAULT_MIN_ALPHA = 0.3f
    private val mMinAlpha = DEFAULT_MIN_ALPHA

    private val DEFAULT_MAX_ROTATE = 10.0f
    private val mMaxRotate = DEFAULT_MAX_ROTATE
    override fun transformPage(view: View, position: Float) {
        Log.e("TAG", "transformPage:  $position" )
        when {
            position < -1 -> {
                //
                view.alpha = mMinAlpha
                view.rotation = mMaxRotate * -1
                view.pivotX = view.width.toFloat()
                view.pivotY = view.height.toFloat()
            }
            position < 0 -> {
                // position 是 0 到 -1  那 1+position 是  1 - 0
                // (1 - mMinAlpha) * (1 + position)  是 (1 - mMinAlpha) 到 0
                // mMinAlpha + (1 - mMinAlpha) * (1 + position)  就是  1 - mMinAlpha
//                val p = 1 + position
                val p = mMinAlpha + (1 - mMinAlpha) * (1 + position)
                view.alpha = p
                view.rotation = mMaxRotate * position
                // 为width/2到width的变化
                // 0.5 * width
                view.pivotX = view.width * 0.5f * (1 - position)
                view.pivotY = view.height.toFloat()
            }
            position <= 1 -> {
                // position 是 0 到 1  那 1 - position 是  1 - 0
                // (1 - mMinAlpha) * (1 - position)  是 (1- mMinAlpha) 到 0
                // mMinAlpha + (1 - mMinAlpha) * (1 - position)   就是   1 到 mMinAlpha
                val p = mMinAlpha + (1 - mMinAlpha) * (1 - position)
                view.alpha = p
                view.rotation = mMaxRotate * position
                view.pivotX = view.width * 0.5f * (1 - position)
                view.pivotY = view.height.toFloat()
            }
            else -> {
                view.alpha = mMinAlpha
                view.rotation = mMaxRotate
                view.pivotX = 0.toFloat()
                view.pivotY = view.height.toFloat()
            }
        }


    }

}