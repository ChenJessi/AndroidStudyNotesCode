package com.chencc.androidstudynotescode.draw_text

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.ViewPager2Adapter
import com.chencc.androidstudynotescode.view_dispatch.RecyclerViewFragment
import kotlinx.android.synthetic.main.activity_color_change.*

/**
 * 文字辅助线
 */
class AssistLineActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assist_line)
    }
}


/**
 * 文字测量演示
 */
class TextMeasureActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextMeasureView(this))
//        setContentView(ColorChangeTextView(this))
    }
}

private const val TAG = "AssistLineActivity"

/**
 * 变色文字 + ViewPager
 */
class ColorChangeActivity : AppCompatActivity() {

    private val mViewList by lazy { mutableListOf<ColorChangeTextView>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_color_change)

        viewPager.adapter = ViewPager2Adapter(this@ColorChangeActivity, initReacyclerViewData())
        viewPager.registerOnPageChangeCallback(initChangeCallback())

//        tvColorChange.setDirection(DIRECTION_BOTTOM)
//        tvColorChange2.setDirection(DIRECTION_BOTTOM)
//        tvColorChange3.setDirection(DIRECTION_BOTTOM)
        mViewList.add(tvColorChange)
        mViewList.add(tvColorChange2)
        mViewList.add(tvColorChange3)
    }

    private fun initReacyclerViewData(): MutableList<Fragment> {
        return mutableListOf<Fragment>().apply {
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
            add(RecyclerViewFragment())
        }
    }

    private fun initChangeCallback(): ViewPager2.OnPageChangeCallback {
        return object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                Log.e(TAG, "onPageScrolled:   $position     $positionOffset   $positionOffsetPixels}")

                if (positionOffset > 0) {
                    val left = mViewList[position]
                    left.setDirection(DIRECTION_RIGHT)
                    left.setPercent(1 - positionOffset)
                    val right = mViewList[position + 1]
                    right.setDirection(DIRECTION_LEFT)
                    right.setPercent(positionOffset)
                }


//                when(position){
//                    0 -> {
//
//                        tvColorChange.setTextChangeColor(Color.BLACK)
//                        tvColorChange.setTextColor(Color.RED)
//                        tvColorChange.setPercent(positionOffset)
//                        tvColorChange2.setTextColor(Color.BLACK)
//                        tvColorChange2.setTextChangeColor(Color.RED)
//                        tvColorChange2.setPercent(positionOffset)
//                    }
//                    1 -> {
//                        tvColorChange2.setTextChangeColor(Color.BLACK)
//                        tvColorChange2.setTextColor(Color.RED)
//                        tvColorChange2.setPercent(positionOffset)
//                        tvColorChange3.setTextColor(Color.BLACK)
//                        tvColorChange3.setTextChangeColor(Color.RED)
//                        tvColorChange3.setPercent(positionOffset)
//                    }
//                    2 -> {
//                        tvColorChange3.setTextChangeColor(Color.BLACK)
//                        tvColorChange3.setTextColor(Color.RED)
//                        tvColorChange3.setPercent(positionOffset)
//                    }
//                }

            }

            override fun onPageSelected(position: Int) {

            }
        }
    }

}


