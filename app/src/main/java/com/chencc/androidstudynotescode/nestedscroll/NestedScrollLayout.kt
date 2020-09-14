package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.net.nsd.NsdManager
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.widget.NestedScrollView
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.utils.FlingHelper
import com.qmuiteam.qmui.util.QMUIDeviceHelper
import com.qmuiteam.qmui.util.QMUIDisplayHelper

/**
 * tab ������ nestedScrollLayout
 */
class NestedScrollLayout : NestedScrollView {
    private val TAG = "NestedScrollLayout"

    private val flingHelper by lazy { FlingHelper(context) }

    private var contentView : ViewGroup? = null
    private var topView : View? = null
    // �����ж� recyclerView �Ƿ��� fling
    private var isStartFling = false

    /**
     * y�Ử���ľ���
     */
    private var totalDy = 0

    /**
     * ��ǰ������y����ٶ�
     */
    private var velocityY = 0

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    init {
        init()
    }

    private fun init() {

        setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if (isStartFling) {
                totalDy = 0
                isStartFling = false
            }
            if (scrollY == 0){
                Log.i(TAG, "TOP SCROLL")
            }
            Log.i(TAG, "SCROLL =====>>>  ${scrollY} ");
            //  getChildAt(0)  ��View �ĸ߶�
            //  v  scrollView �ĸ߶�
            // getChildAt(0).measuredHeight - v.measuredHeight == �������ϻ�����������
            if (scrollY == (getChildAt(0).measuredHeight - v.measuredHeight)){
                Log.i(TAG, "BOTTOM SCROLL");
                dispatchChildFling()
            }
            // �� recyclerView fling����£���¼recyclerView��y���λ��
            totalDy += scrollY - oldScrollY
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        topView = (getChildAt(0) as ViewGroup).getChildAt(0)
        contentView = (getChildAt(0) as ViewGroup).getChildAt(1) as ViewGroup
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        // ����contentView�߶�Ϊ�������߶ȣ�ʹ֮��䲼�֣������������ֿհ�
        val layoutParams = contentView?.layoutParams
        layoutParams?.height = measuredHeight
        contentView?.layoutParams = layoutParams
    }

    /**
     * ����֮ǰ
     */
    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        // ���Ϲ���������ǰtopView �ɼ�����Ҫ��topView ���������ɼ�
        val hideTop = dy > 0 && scrollY < topView?.measuredHeight ?: 0
        if (hideTop){
          scrollBy(0, dy)
          consumed[1] = dy
        }
    }
    override fun fling(velocityY: Int) {
        super.fling(velocityY)
        if (velocityY <= 0){
            this.velocityY = 0
        }else {
            isStartFling = true
            this.velocityY = velocityY
        }
    }







    private fun dispatchChildFling(){
        if (velocityY != 0){
            // ���㻬���ľ���
            var splineFlingDistance = flingHelper.getSplineFlingDistance(velocityY)
            if (splineFlingDistance > totalDy){
                // ������ݼ��ٶȼ�����Ļ������������󻬶�����Ļ�
                // �ͽ���ǰʣ�໬�������������ٶȽ�����View ȥ��������
                childFling(flingHelper.getVelocityByDistance(splineFlingDistance - totalDy))
            }
        }
        totalDy = 0;
        velocityY = 0;
    }

    /**
     * ����y���ٶȣ���recyclerView�������Ի���
     */
    private fun childFling(velY : Int){
        contentView?.let {
            val childRecyclerView = getChildRecyclerView(it)
            childRecyclerView?.fling(0, velY)
        }
    }


    /**
     * ������view ����recyclerView
     */
    private fun getChildRecyclerView(viewGroup : ViewGroup) : RecyclerView?{
        for (index in 0 until viewGroup.childCount ){
            val view = viewGroup.getChildAt(index)
            if (view is RecyclerView){
                return view
            }else if (view is ViewGroup){
                val childRecyclerView = getChildRecyclerView(view)
                if (childRecyclerView is RecyclerView){
                    return childRecyclerView
                }
            }
        }
        return null
    }
}