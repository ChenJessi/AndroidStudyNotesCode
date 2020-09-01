package com.chencc.androidstudynotescode.skin

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.view.LayoutInflaterCompat
import java.util.*

/**
 * @author Created by CHEN on 2020/8/27
 * @email 188669@163.com
 */
class ApplicationActivityLifecycle(var obserable : Observable) : Application.ActivityLifecycleCallbacks {

    private val mLayoutInflaterFactories = mutableMapOf<Activity, SkinLayoutInflaterFactory>()


    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        /**
         * 更新状态栏
         */

        /**
         * 更新视图布局
         */
        // 获得 activity 的布局加载器
        val layoutInflater = activity.layoutInflater

        try {
            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            field.isAccessible = true
            field.setBoolean(layoutInflater, false)
            layoutInflater.factory2
        } catch (e: Exception) {
            e.printStackTrace()
        }
        val skinLayoutInflaterFactory = SkinLayoutInflaterFactory(activity)
        LayoutInflaterCompat.setFactory2(layoutInflater, skinLayoutInflaterFactory)
        mLayoutInflaterFactories[activity] = skinLayoutInflaterFactory
        obserable.addObserver(skinLayoutInflaterFactory)
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStarted(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        val remove = mLayoutInflaterFactories.remove(activity)
        SkinManager.deleteObserver(remove)
    }

}