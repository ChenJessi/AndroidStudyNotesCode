package com.chencc.androidstudynotescode.utils.screendensity

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle

/**
 * 屏幕适配
 */


/**
 * 屏幕适配的基准
 */
private const val MATCH_BASE_WIDTH = 0
private const val MATCH_BASE_HEIGHT = 1


object ScreenDensityUtils {


    private lateinit var sMatchInfo: MatchInfo

    // Activity 的生命周期监测
    private  var mActivityLifecycleCallback: Application.ActivityLifecycleCallbacks? = null
    /**
     * 初始化
     */
    fun setup(application: Application){

        /**
         *  获取屏幕分辨率信息的三种方法
         *  第一种：
            DisplayMetrics metrics = new DisplayMetrics();
            Display display = activity.getWindowManager().getDefaultDisplay();
            display.getMetrics(metrics);

         *  第二种：
             DisplayMetrics metrics= activity.getResources().getDisplayMetrics();

         *  第三种：
            Resources.getSystem().getDisplayMetrics();
         */

        // 注意这个是获取系统的displayMetrics
        val displayMetrics = application.resources.displayMetrics

        if (!::sMatchInfo.isInitialized){
            // 记录系统的原始值
            sMatchInfo = MatchInfo().apply {
                screenWidth = displayMetrics.widthPixels
                screenHeight = displayMetrics.heightPixels
                appDensity = displayMetrics.density
                appDensityDpi = displayMetrics.densityDpi.toFloat()
                appScaledDensity = displayMetrics.scaledDensity
            }
        }

        /**
         * 添加字体变化的监听
         * 调用 Application#registerComponentCallbacks 注册下 onConfigurationChanged 监听即可。
         */
        application.registerComponentCallbacks(object : ComponentCallbacks {
            override fun onConfigurationChanged(newConfig: Configuration) {
                // 字体改变后，将 appScaledDensity 重新赋值
                if (newConfig?.fontScale > 0) {
                    val scaledDensity = displayMetrics.scaledDensity
                    sMatchInfo.appScaledDensity = scaledDensity
                }
            }

            override fun onLowMemory() {

            }
        })

    }


    /**
     *  在 application 中全局激活配置，（也可以单独使用 match() 方法在指定页面中配置适配）
     */
    fun register(application: Application, designSize: Float, matchBase: Int){
        if (mActivityLifecycleCallback == null){
            mActivityLifecycleCallback = object : Application.ActivityLifecycleCallbacks{
                override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                    match(activity, designSize, matchBase)
                }

                override fun onActivityStarted(activity: Activity) {

                }

                override fun onActivityResumed(activity: Activity) {

                }

                override fun onActivityPaused(activity: Activity) {

                }

                override fun onActivityStopped(activity: Activity) {

                }

                override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

                }

                override fun onActivityDestroyed(activity: Activity) {}
            }
        }
        application.registerActivityLifecycleCallbacks(mActivityLifecycleCallback)

    }

    /**
     * 全局取消所有适配
     */
    fun unregister(application: Application) {
        if (mActivityLifecycleCallback != null){
            application.unregisterActivityLifecycleCallbacks(mActivityLifecycleCallback)
            mActivityLifecycleCallback = null
        }
        cancelMatch(application)
    }

    /**
     * 适配屏幕（放在 Activity 的 setContentView() 之前执行）
     *
     * @param designSize 设计图尺寸
     * @param matchBase 适配基准
     * @param matchUnit 使用的适配单位
     */
    fun match(context: Context, designSize: Float, matchBase: Int = MATCH_BASE_WIDTH) {
        if (designSize == 0f){
            throw UnsupportedOperationException("The designSize cannot be equal to 0")
        }

        matchByDP(context, designSize, matchBase)
    }


    /**
     * 重置配置信息， 取消适配
     */
    fun cancelMatch(application: Application) {
       val displayMetrics = application.resources.displayMetrics.apply {
           if (density != sMatchInfo.appDensity){
               density = sMatchInfo.appDensity
           }
           densityDpi = sMatchInfo.appDensityDpi.toInt()
           scaledDensity = sMatchInfo.appScaledDensity
       }
    }


    /**
     *  使用 dp 作为适配单位，
     *  dp 与 px 换算
     *  px = density * dp
     *  density = dpi / 160
     *  px = dp * (dpi / 160)
     *
     *
     *  @param designSize 设计图的宽/高（单位: dp）
     *  @param matchBase 适配基准
     */
    private fun matchByDP(context: Context, designSize : Float, matchBase : Int){
        val targetDensity = when(matchBase){
            MATCH_BASE_WIDTH -> {
                sMatchInfo.screenWidth * 1f / designSize
            }
            MATCH_BASE_HEIGHT -> {
                sMatchInfo.screenHeight * 1f / designSize
            }
            else -> {
                sMatchInfo.screenWidth * 1f / designSize
            }
        }

        val targetDensityDpi = targetDensity * 160
        val targetScaledDensity = targetDensity * (sMatchInfo.appScaledDensity / sMatchInfo.appDensity)
        val displayMetrics = context.resources.displayMetrics.apply {
            density = targetDensity
            densityDpi = targetDensityDpi.toInt()
            scaledDensity = targetScaledDensity
        }
    }


    private data class MatchInfo(
        var screenWidth: Int = 0,
        var screenHeight: Int = 0,
        var appDensity: Float = 0f,
        var appDensityDpi: Float = 0f,
        var appScaledDensity: Float = 0f
    )
}