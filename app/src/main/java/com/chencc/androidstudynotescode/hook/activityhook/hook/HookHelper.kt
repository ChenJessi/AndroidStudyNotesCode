package com.chencc.androidstudynotescode.hook.activityhook.hook

import android.app.Activity
import android.app.Instrumentation
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.os.*
import android.util.Log
import com.chencc.androidstudynotescode.hook.activityhook.StubActivity
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Proxy


/**
 * @author Created by CHEN on 2020/12/30
 * @email 188669@163.com
 */
private const val TAG = "HookHelper"
private const val EXTRA_TARGET_INTENT = "extra_target_intent"

fun hookAMSAidl(){
    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P){
        hookIActivityTaskManager()
    }else{
        hookIActivityManager()
    }

}


private fun hookIActivityTaskManager(){

    try {
        val activityManager = Class.forName("android.app.ActivityTaskManager")
        val singletonField = activityManager.getDeclaredField("IActivityTaskManagerSingleton").apply {
            isAccessible = true
        }
        val singleton = singletonField.get(null)
        // 拿到 IActivityTaskManager 对象
        val singletonClass = Class.forName("android.util.Singleton")
        val mInstanceField = singletonClass.getDeclaredField("mInstance").apply {
            isAccessible = true
        }
        // 原始的IActivityTaskManager
        val IActivityTaskManager = mInstanceField.get(singleton)

        val proxy = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
                            arrayOf(Class.forName("android.app.IActivityTaskManager")),
                            InvocationHandler { proxy, method, args ->
                                Log.i(TAG, "hookIActivityTaskManager:  ${method.name}")
                                var raw : Intent? = null
                                var index = -1
                                if ("startActivity" == method.name){
                                    Log.i(TAG, "invoke : startActivity 启动准备")
                                    args.forEachIndexed { i, any ->
                                        if (any is Intent){
                                            raw = any
                                            index = i
                                        }
                                    }
                                    Log.i(TAG, "invoke :  raw  $raw")
                                    val newIntent = Intent().apply {
                                        component = ComponentName("com.chencc.androidstudynotescode", StubActivity::class.java.name)
                                        putExtra(EXTRA_TARGET_INTENT, raw)
                                    }
                                    args[index] = newIntent;
                                }
                                method.invoke(IActivityTaskManager, *args)
                            })

        mInstanceField.set(singleton, proxy)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private fun hookIActivityManager(){
    try {
        val singletonField = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val activityManager = Class.forName("android.app.ActivityManager")
            activityManager.getDeclaredField("IActivityManagerSingleton")
        }else {
            val activityManager = Class.forName("android.app.ActivityManagerNative")
            activityManager.getDeclaredField("gDefault")
        }.apply {
            isAccessible = true
        }
        val singleton = singletonField.get(null)
        //拿IActivityManager对象
        val singletonClass = Class.forName("android.util.Singleton")
        val mInstanceField = singletonClass.getDeclaredField("mInstance").apply {
            isAccessible = true
        }
        //原始的IActivityManager
        val rawIActivityManager = mInstanceField.get(singleton)
        //创建一个IActivityManager代理对象
        val proxy = Proxy.newProxyInstance(Thread.currentThread().contextClassLoader,
            arrayOf(Class.forName("android.app.IActivityManager")),
            InvocationHandler { proxy, method, args ->
                Log.i(TAG, "hookIActivityManager:  method : ${method.name}")
                // 在这里换掉真正要启动的
                var raw : Intent? = null
                var index = -1
                if ("startActivity" == method.name){
                    Log.i(TAG, "invoke : startActivity 启动准备")
                    args.forEachIndexed { i, any ->
                        if (any is Intent){
                            raw = any
                            index = i
                        }
                    }
                    Log.i(TAG, "invoke:  raw : ${raw}")
                    //代替的Intent
                    val newIntent = Intent().apply {
                        component = ComponentName("com.chencc.androidstudynotescode", StubActivity::class.java.name)
                        putExtra(EXTRA_TARGET_INTENT, raw)
                    }
                    args[index] = newIntent
                }
                method.invoke(rawIActivityManager, *args)
            })
        mInstanceField.set(singleton, proxy)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}



fun hookHandler(){
    try {
        val forName = Class.forName("android.app.ActivityThread")
        val sCurrentActivityThreadField = forName.getDeclaredField("sCurrentActivityThread").apply {
            isAccessible = true
        }
        val sCurrentActivityThread = sCurrentActivityThreadField.get(null)

        val mHField = forName.getDeclaredField("mH").apply {
            isAccessible = true
        }
        val mH = mHField.get(sCurrentActivityThread) as Handler
        val mCallbackField = Handler::class.java.getDeclaredField("mCallback").apply {
            isAccessible = true
        }.set(mH, object : Handler.Callback {
            override fun handleMessage(msg: Message): Boolean {
                Log.i(TAG, "handleMessage:  what :  ${msg.what}  obj  : ${msg?.obj::class?.java?.canonicalName}")
                when(msg.what){
                    //从AndroidP开始重构了状态模式
                    //public static final int EXECUTE_TRANSACTION = 159;
                    // 首先一个app 只有一个ActivityThread 然后就只有一个mH
                    //我们app所有的activity的生命周期的处理都在mH的handleMessage里面处理
                    //在Android 8.0之前，不同的生命周期对应不同的msg.what处理
                    //在Android 9.0 改成了全部由EXECUTE_TRANSACTION来处理
                    //所以这里第一次mActivityCallbacks是MainActivity的生命周期回调的
                    100 ->{
                        val className = "android.app.ActivityThread.ActivityClientRecord"
                        val obj = msg.obj
                        if (obj::class.java.canonicalName == className){
                            val intentField =  obj::class.java.getDeclaredField("intent").apply {
                                isAccessible = true
                            }
                            val intent = intentField.get(obj) as Intent
                            intent.apply {
                                val targetIntent = getParcelableExtra<Intent>(EXTRA_TARGET_INTENT)
                                intent.component = targetIntent.component
                            }
                        }
                    }
                    159 -> {
                        val obj = msg.obj
                        Log.i(TAG, "handleMessage:  obj : $obj")

                        try {
                            val mActivityCallbacksField = obj::class.java.getDeclaredField("mActivityCallbacks").apply {
                                isAccessible = true
                            }
                            val mActivityCallbacks = mActivityCallbacksField.get(obj) as List<Any>
                            if (mActivityCallbacks.isNotEmpty()){
                                Log.i(TAG, "handleMessage: mActivityCallbacks size : ${mActivityCallbacks.size}")
                                val className = "android.app.servertransaction.LaunchActivityItem"
                                if (mActivityCallbacks[0]::class.java.canonicalName == className){
                                    val obj = mActivityCallbacks[0]
                                    val intentField = obj::class.java.getDeclaredField("mIntent").apply {
                                        isAccessible = true
                                    }
                                    val intent = intentField.get(obj) as Intent
                                    intent.apply {
                                        val targetIntent = getParcelableExtra<Intent>(EXTRA_TARGET_INTENT)
                                        intent.component = targetIntent.component
                                    }
                                }
                            }
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
                mH.handleMessage(msg);
                return true
            }
        })
    } catch (e: Exception) {
        Log.i(TAG, "hookHandler:  e :  ${e.message}")
        e.printStackTrace()
    }
}


fun hookInstrumentation(activity : Activity){
    try {
        val activityClass = activity::class.java
        // 通过 activity.class 拿到 mInstrumentation 字段
        val mInstrumentationField = activityClass.getDeclaredField("mInstrumentation").apply {
            isAccessible = true
        }
        val mInstrumentation = mInstrumentationField.get(activity) as Instrumentation
        //创建代理对象,注意了因为Instrumentation是类，不是接口 所以我们只能用静态代理
        val instrumentationProxy = ProxyInstrumentation(mInstrumentation)
        // 替换
        mInstrumentationField.set(activity, instrumentationProxy)
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

private class ProxyInstrumentation(val mBase : Instrumentation) : Instrumentation(){

    fun execStartActivity(
        who: Context?, contextThread: IBinder?, token: IBinder?, target: Activity?,
        intent: Intent?, requestCode: Int, options: Bundle?
    ): ActivityResult?{
        Log.d(TAG, "执行了startActivity, 参数如下: " + "who = [" + who + "], " +
                "contextThread = [" + contextThread + "], token = [" + token + "], " +
                "target = [" + target + "], intent = [" + intent +
                "], requestCode = [" + requestCode + "], options = [" + options + "]");

        try {
            val execStartActivity = Instrumentation::class.java.getDeclaredMethod("execStartActivity",
                Context::class.java, IBinder::class.java, IBinder::class.java , Activity::class.java ,
                Intent::class.java, Int::class.java, Bundle::class.java).apply {
                isAccessible = true
            }
            return execStartActivity.invoke(mBase, who,
                contextThread, token, target, intent, requestCode, options) as ActivityResult
        } catch (e: Exception) {
            throw  RuntimeException("do not support!!! pls adapt it")
        }
    }

    override fun newActivity(cl: ClassLoader?, className: String?, intent: Intent?): Activity {
        return mBase.newActivity(cl, className, intent)
    }
}

fun hookActivityThreadInstrumentation(){
    try {
        val activityThreadClass = Class.forName("android.app.ActivityThread")
        val currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread").apply {
            isAccessible = true
        }
        // threadActivity 对象
        val currentActivityThread = currentActivityThreadMethod.invoke(null)

        val mInstrumentationField = activityThreadClass.getDeclaredField("mInstrumentation").apply {
            isAccessible = true
        }
        val mInstrumentation = mInstrumentationField.get(currentActivityThread) as Instrumentation
        val instrumentationProxy = ProxyInstrumentation(mInstrumentation)
        mInstrumentationField.set(currentActivityThread, instrumentationProxy)

    } catch (e: Exception) {
        e.printStackTrace()
    }
}