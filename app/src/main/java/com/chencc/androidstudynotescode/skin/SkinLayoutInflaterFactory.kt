package com.chencc.androidstudynotescode.skin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.ThemeUtils
import kotlinx.coroutines.currentCoroutineContext
import java.lang.reflect.Constructor
import java.util.*

/**
 * @author Created by CHEN on 2020/8/27
 * @email 188669@163.com
 */
class SkinLayoutInflaterFactory(var activity: Activity) : LayoutInflater.Factory2 , Observer{
    private val mClassPrefixList = arrayOf(
        "android.widget.",
        "android.webkit.",
        "android.app.",
        "android.view."
    )

    // 记录对应 View 的构造函数
    private val sConstructorMap = mutableMapOf<String, Constructor<out View?>>()
    private val mConstructorSignature = arrayOf<Class<*>>(Context::class.java, AttributeSet::class.java)
    // 需要查找替换view 的属性
    val skinAttribute = SkinAttribute()

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        // 换肤就是在这里替换view的属性资源
        // 所以在这里创建view 修改属性
        //  在 name 前面拼前缀，反射创建 SDK内部的View  如 TextView , ImageView 等
        var view = createSDKView(name, context, attrs)
        // 如果 view == null  说明 是 androidx 支持包或者我们的自定义view 不用拼前缀直接反射创建
        if (view == null){
            view = createView(name, context, attrs)
        }
        // view 不为空，收集View 需要换肤的属性并保存
        if (view != null){
            skinAttribute.look(view, attrs)
        }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    /**
     * 创建 SDK 中的 View
     * 如 ： TextView  ImageView 等
     * 逻辑参考 LayoutInflater.createViewFromTag(...)
     */
    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        // 如果包含 . 则不是SDK 中的View( 如 TextView 等)   可能是  androidx 支持包或者我们的自定义view
        if (-1 != name.indexOf(".")) {
            return null
        }
        // 不包含的话就要在解析的节点 name 之前 拼上  android.widget.  尝试去反射 创建
        mClassPrefixList.forEachIndexed { index, s ->
            val view = createView(s + name, context, attrs)
            if (view != null){
                return view
            }
        }
        return null
    }

    /**
     * 反射获取view 的构造函数
     *
     */
    private fun findConstructor(name: String, context: Context) : Constructor<out View>? {
        var constructor = sConstructorMap.get(name)
        if (constructor == null) {
            try {
                // Class not found in the cache, see if it's real, and try to add it
                val clazz = context.classLoader.loadClass(name).asSubclass(View::class.java)
                constructor = clazz.getConstructor(*mConstructorSignature)
                constructor.isAccessible = true
                sConstructorMap[name] = constructor
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        return constructor
    }

    /**
     *  反射创建 View
     */
    private fun createView(name: String, context: Context, attrs: AttributeSet) : View? {
        val constructor = findConstructor(name, context)
        try {
            return constructor?.newInstance(context, attrs)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    // 接收到被观察者发送的消息，执行  改变view皮肤
    override fun update(o: Observable?, arg: Any?) {
        skinAttribute.applySkin()
    }

}