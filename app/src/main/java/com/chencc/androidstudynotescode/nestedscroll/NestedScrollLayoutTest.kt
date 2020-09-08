package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.widget.NestedScrollView


class NestedScrollLayoutTest : NestedScrollView {
    private var contentView: ViewGroup? = null
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )



    override fun onFinishInflate() {
        super.onFinishInflate()
        contentView = (getChildAt(0) as ViewGroup?)?.getChildAt(1) as ViewGroup?
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // ����contentView�ĸ߶�Ϊ�������߶ȣ�ʹ֮��䲼�֣����⸸������������ֿհ�
        // �� tabLayout + viewPager �߶�����Ϊ�����ָ߶ȣ�Ҳ������Ļ�߶�
        val lp = contentView?.layoutParams
        lp?.height = measuredHeight
        contentView?.layoutParams = lp
    }
}