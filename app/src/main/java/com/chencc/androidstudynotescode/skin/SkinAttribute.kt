package com.chencc.androidstudynotescode.skin

import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import com.chencc.androidstudynotescode.utils.getResId

/**
 * @author Created by CHEN on 2020/8/24
 * @email 188669@163.com
 */

/**
 * Ҫ������ View ������
 */
class SkinAttribute {
    private val mAttribute = mutableListOf<String>(
        "background",
        "src",
        "textColor",
        "drawableLeft",
        "drawableTop",
        "drawableRight",
        "drawableBottom"
    )
    private val mSkinViews = mutableListOf<SkinView>()

    /**
     * ����ÿ�� view ����������
     * �ҳ���Ҫ���������Բ���¼ �������� �� ��Դid
     */
    fun look(view: View, attr: AttributeSet) {
        val mSkinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attr.attributeCount) {
            // ��ȡ������
            val attributeName = attr.getAttributeName(i)
            // �м����е����Բ���Ҫ����
            if (mAttribute.contains(attributeName)) {
                val attributeValue = attr.getAttributeValue(i)
                //  xml д������ɫ���ܻ��� #
                if (attributeValue.startsWith("#")) {
                    return
                }
                var resId: Int
                // ����ͷ�ı�ʾϵͳ��Դ
                if (attributeValue.startsWith("?")) {
                    // ��ȡid
                    val attrId = attributeValue.substring(1).toInt()
                    resId = getResId(view.context, IntArray(1) { attrId })[0]
                } else {
                    // ʣ�µ� ���� @ ��ͷ�� �����Լ�����Դ
                    resId = attributeValue.substring(1).toInt()
                    Log.e("TAG", "look:  $resId   ${attributeValue}")

                }
                mSkinPairs.add(SkinPair(attributeName, resId))
            }
        }
        // �������Ҫ���������� ���� ʵ���� SkinViewSupport �ӿڶ���ʾ��Ҫ����
        if (mSkinPairs.isNotEmpty() || view is SkinViewSupport) {
            val skinView = SkinView(view, mSkinPairs)
            //  ���ѡ�񻻷� �ȵ���һ�� ����Ƥ������Դ
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    /**
     * ������ View ���л���
     */
    fun applySkin() {
        mSkinViews.forEach {
            it.applySkin()
        }
    }
}

/**
 * Ҫ������View
 * View ��Ҫ���������Ժ���Դid����
 */
class SkinView(var view: View, var mSkinPairs: MutableList<SkinPair> = mutableListOf()) :
    SkinViewSupport {

    /**����������Ҫ�޸ĵ�����
     *
     * ��view��ÿһ�����Խ����޸�
     */
    override fun applySkin() {
        mSkinPairs.forEach {
            applySkinSupport()
            val attributeName = it.attributeName
            var left : Drawable? = null
            var top : Drawable? = null
            var right : Drawable? = null
            var bottom : Drawable? = null
            when (attributeName) {
                "background" -> {
                    val background = SkinResources.getBackground(it.resId)
                    // background Ϊ color ʱ Ϊ int ����
                    if (background is Int) {
                        view.setBackgroundColor(background)
                    } else if (background is Drawable) {
                        ViewCompat.setBackground(view, background)
                    }
                }
                "src" -> {
                    val src = SkinResources.getBackground(it.resId)

                    if (view is ImageView) {
                        if (src is Int) {
                            (view as ImageView).setImageDrawable(ColorDrawable(src))
                        }else if (src is Drawable){
                            (view as ImageView).setImageDrawable(src)
                        }
                    }
            }
            "textColor" ->{
//                val textColor = SkinResources.getColorStateList(it.resId)
                val textColor = SkinResources.getColor(it.resId)
                if (view is TextView){
                    (view as TextView).setTextColor(textColor)
                }
            }
            "drawableLeft" ->{
                left = SkinResources.getDrawable(it.resId)
            }
            "drawableTop" ->{
                top = SkinResources.getDrawable(it.resId)
            }
            "drawableRight" ->{
                right = SkinResources.getDrawable(it.resId)
            }
            "drawableBottom" ->{
                bottom = SkinResources.getDrawable(it.resId)
            }
        }
         if (view is TextView){
             (view as TextView).setCompoundDrawables(left, top, right, bottom)
         }
    }
}

/**
 * �޸�ʵ���� SkinViewSupport ��view����
 */
private fun applySkinSupport() {
    if (view is SkinViewSupport) {
        (view as SkinViewSupport).applySkin()
    }
}
}

/**
 * Ҫ���������Լ���
 * ������-��Դid
 */
data class SkinPair(var attributeName: String, var resId: Int)

