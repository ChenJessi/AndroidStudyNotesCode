package com.chencc.androidstudynotescode.hook.activityhook.hook

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Handler
import android.os.Message
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

@Throws(Exception::class)
private fun hookIActivityTaskManager(){

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
                                    component = ComponentName("com.chencc.androidstudynotescode.hook.activityhook", StubActivity::class.java.name)
                                    putExtra(EXTRA_TARGET_INTENT, raw)
                                }

                                args[index] = newIntent;
                            }
                            return@InvocationHandler method.invoke(IActivityTaskManager, args)
                        })

    mInstanceField.set(singleton, proxy)
}


@Throws(Exception::class)
private fun hookIActivityManager(){

}


@Throws(Exception::class)
fun hookHandler(){
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
            Log.i(TAG, "handleMessage:  what :  ${msg.what}")
            when(msg.what){
                159 -> {
                    val obj = msg.obj
                    Log.i(TAG, "handleMessage:  obj : $obj")
                }
            }
            return true
        }
    })


}
