package com.chencc.androidstudynotescode.customview

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import com.chencc.androidstudynotescode.R


/**
 *  自定义 ViewGroup
 */
class ViewGroupTest(private val mContext: Context) : ViewGroup(mContext){

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {

    }


    /**
     *  检查 LayoutParams 是否合法
     */
    override fun checkLayoutParams(p: LayoutParams?): Boolean {
        return p is MarginLayoutParamsTest
    }

    /**
     * 生成默认的 LayoutParams
     */
    override fun generateDefaultLayoutParams(): LayoutParams {
        return MarginLayoutParamsTest(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    /**
     * 对传入的 layoutParams 进行转化
     */
    override fun generateLayoutParams(p: LayoutParams?): LayoutParams {
        return MarginLayoutParamsTest(p)
    }
    /**
     * 对传入的 layoutParams 进行转化
     */
    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return  MarginLayoutParamsTest(context, attrs)
    }
}




/**
 * 自定义 LayoutParams
 */
class MarginLayoutParamsTest : ViewGroup.MarginLayoutParams{
    var simpleAttr : Int = 0
    var gravity : Int = 0

    constructor(c: Context?, attrs: AttributeSet?) : super(c, attrs){
        // 解析布局属性
        val typedArray = c?.obtainStyledAttributes(attrs, R.styleable.SimpleViewGroup_Layout)
        simpleAttr = typedArray?.getInteger(R.styleable.SimpleViewGroup_Layout_layout_simple_attr, 0) ?: 0
        gravity = typedArray?.getInteger(R.styleable.SimpleViewGroup_Layout_android_layout_gravity, -1) ?: 0
        typedArray?.recycle()
    }
    constructor(width: Int, height: Int) : super(width, height)
    constructor(source: ViewGroup.MarginLayoutParams?) : super(source)
    constructor(source: ViewGroup.LayoutParams?) : super(source)

}