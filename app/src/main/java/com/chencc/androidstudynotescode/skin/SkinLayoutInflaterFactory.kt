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

    // ��¼��Ӧ View �Ĺ��캯��
    private val sConstructorMap = mutableMapOf<String, Constructor<out View?>>()
    private val mConstructorSignature = arrayOf<Class<*>>(Context::class.java, AttributeSet::class.java)
    // ��Ҫ�����滻view ������
    val skinAttribute = SkinAttribute()

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        // ���������������滻view��������Դ
        // ���������ﴴ��view �޸�����
        //  �� name ǰ��ƴǰ׺�����䴴�� SDK�ڲ���View  �� TextView , ImageView ��
        var view = createSDKView(name, context, attrs)
        // ��� view == null  ˵�� �� androidx ֧�ְ��������ǵ��Զ���view ����ƴǰ׺ֱ�ӷ��䴴��
        if (view == null){
            view = createView(name, context, attrs)
        }
        // view ��Ϊ�գ��ռ�View ��Ҫ���������Բ�����
        if (view != null){
            skinAttribute.look(view, attrs)
        }
        return view
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    /**
     * ���� SDK �е� View
     * �� �� TextView  ImageView ��
     * �߼��ο� LayoutInflater.createViewFromTag(...)
     */
    private fun createSDKView(name: String, context: Context, attrs: AttributeSet): View? {
        // ������� . ����SDK �е�View( �� TextView ��)   ������  androidx ֧�ְ��������ǵ��Զ���view
        if (-1 != name.indexOf(".")) {
            return null
        }
        // �������Ļ���Ҫ�ڽ����Ľڵ� name ֮ǰ ƴ��  android.widget.  ����ȥ���� ����
        mClassPrefixList.forEachIndexed { index, s ->
            val view = createView(s + name, context, attrs)
            if (view != null){
                return view
            }
        }
        return null
    }

    /**
     * �����ȡview �Ĺ��캯��
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
     *  ���䴴�� View
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

    // ���յ����۲��߷��͵���Ϣ��ִ��  �ı�viewƤ��
    override fun update(o: Observable?, arg: Any?) {
        skinAttribute.applySkin()
    }

}