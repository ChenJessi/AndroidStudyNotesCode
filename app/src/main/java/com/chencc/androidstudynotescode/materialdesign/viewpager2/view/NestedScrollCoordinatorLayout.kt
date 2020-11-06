package com.chencc.androidstudynotescode.materialdesign.viewpager2.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.annotation.IntDef
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.NestedScrollingChild
import androidx.core.view.NestedScrollingChildHelper
import androidx.core.view.ViewCompat


/**
 *
 */

private const val TAG = "NestedScrollCoordinator"


/**
 * Constant for [.setPassMode]. When this is selected, scroll events are
 * passed to the parent stream and, at the same time, to this Coordinator childs.
 */
private const val PASS_MODE_BOTH = 0

/**
 * Constant for [.setPassMode]. When this is selected, scroll events are
 * passed to the parent stream and, if not consumed, they go on to this Coordinator childs.
 */
private const val PASS_MODE_PARENT_FIRST = 1

@Retention(AnnotationRetention.SOURCE)
@IntDef(value = [PASS_MODE_BOTH, PASS_MODE_PARENT_FIRST])
annotation class PassMode{}

/**
 *
 *
 */
class NestedScrollCoordinatorLayout : CoordinatorLayout, NestedScrollingChild {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var helper : NestedScrollingChildHelper = NestedScrollingChildHelper(this)

    private lateinit var dummyBehavior : DummyBehavior<View>

    init {
        //        isNestedScrollingEnabled = false
        // Add a dummy view that will receive inner touch events.
        val dummyView = View(context)
        dummyBehavior = DummyBehavior<View>()
        // I *think* this is needed for dummyView to be identified as "topmost" and receive events
        // before any other view.
        ViewCompat.setElevation(dummyView, ViewCompat.getElevation(this))

        dummyView.fitsSystemWindows = false

        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        params.behavior = dummyBehavior
        addView(dummyView, params)
        setPassMode(10)
    }


    /**
     * Sets the pass mode for this coordinator.
     * @see #PASS_MODE_BOTH
     * @see #PASS_MODE_PARENT_FIRST
     *
     * @param mode desired pass mode for scroll events.
     */
    fun setPassMode(@PassMode mode: Int){
        dummyBehavior.setPassMode(mode)
    }

    override fun setNestedScrollingEnabled(enabled: Boolean) {
        helper.isNestedScrollingEnabled = enabled
    }


    override fun isNestedScrollingEnabled() = helper.isNestedScrollingEnabled

    override fun startNestedScroll(axes: Int): Boolean {
        return helper.startNestedScroll(axes)
    }

    override fun stopNestedScroll() {
        helper.stopNestedScroll()
    }

    override fun hasNestedScrollingParent() = helper.hasNestedScrollingParent()

    override fun dispatchNestedPreScroll(dx: Int, dy: Int, consumed: IntArray?, offsetInWindow: IntArray?): Boolean {
        return helper.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow)
    }

    override fun dispatchNestedScroll(dxConsumed: Int, dyConsumed: Int, dxUnconsumed: Int, dyUnconsumed: Int, offsetInWindow: IntArray?): Boolean {
        return helper.dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow)
    }

    override fun dispatchNestedFling(velocityX: Float, velocityY: Float, consumed: Boolean): Boolean {
        return helper.dispatchNestedFling(velocityX, velocityY, consumed)
    }

    override fun dispatchNestedPreFling(velocityX: Float, velocityY: Float): Boolean {
        return helper.dispatchNestedPreFling(velocityX, velocityY)
    }













    /**
     * This behavior is assigned to our dummy, MATCH_PARENT view inside this bottom sheet layout.
     * Through this behavior the dummy view can listen to touch/scroll events.
     * Our goal is to propagate them to the parent stream.
     *
     * It has to be done manually because by default CoordinatorLayouts don't propagate scroll events
     * to their parent. This is bad for CoordinatorLayouts inside other CoordinatorLayouts, since
     * the coordination works relies heavily on scroll events.
     *
     * @param <DummyView> make sure it's not a nested-scrolling-enabled view or this will break.
     */
    class DummyBehavior<DummyView : View?>: Behavior<DummyView>{

        @PassMode
        private var mode = PASS_MODE_PARENT_FIRST

        private val cache = IntArray(2)
        constructor() : super()
        constructor(mode: Int){
            this.mode = mode
        }
        constructor(context: Context?, attrs: AttributeSet? , mode: Int = PASS_MODE_PARENT_FIRST) : super(context, attrs){
            this.mode = mode
        }


        fun setPassMode(@PassMode mode: Int) {
            this.mode = mode
        }

        override fun onStartNestedScroll(coordinatorLayout: CoordinatorLayout, child: DummyView, directTargetChild: View, target: View, axes: Int, type: Int): Boolean {
           val sheet = coordinatorLayout as NestedScrollCoordinatorLayout
            return sheet.startNestedScroll(axes)
        }

        override fun onStopNestedScroll(coordinatorLayout: CoordinatorLayout, child: DummyView, target: View, type: Int) {
            val sheet = coordinatorLayout as NestedScrollCoordinatorLayout
            sheet.stopNestedScroll()
        }


        //  When moving the finger up, dy is > 0.
        override fun onNestedPreScroll(coordinatorLayout: CoordinatorLayout, child: DummyView, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
            val sheet = coordinatorLayout as NestedScrollCoordinatorLayout
            when (mode) {
                PASS_MODE_PARENT_FIRST -> {
                    sheet.dispatchNestedPreScroll(dx, dy , consumed, null)
                }
                PASS_MODE_BOTH -> {
                    cache[0] = consumed[0]
                    cache[1] = consumed[1]
                    sheet.dispatchNestedPreScroll(dx, dy, cache, null)
                }
            }
        }

        override fun onNestedPreFling(coordinatorLayout: CoordinatorLayout, child: DummyView, target: View, velocityX: Float, velocityY: Float): Boolean {
            val sheet = coordinatorLayout as NestedScrollCoordinatorLayout
            val dispatchNestedPreFling = sheet.dispatchNestedPreFling(velocityX, velocityY)
            return when (mode) {
                PASS_MODE_PARENT_FIRST -> {
                    dispatchNestedPreFling
                }
                else -> {
                    false
                }
            }
        }
        // onNestedScroll and onNestedFling are not needed.
    }

}