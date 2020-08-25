package com.chencc.androidstudynotescode.skin

import android.util.AttributeSet
import android.view.View
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
        "drawableBottom")
    private val mSkinViews = mutableListOf<SkinView>()

    /**
     * ��¼ÿ��view ��Ҫ����������
     */
    fun look(view: View, attr : AttributeSet){
        val mSkinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attr.attributeCount){
            // ��ȡ������
            val attributeName = attr.getAttributeName(i)
            // �м����е����Բ���Ҫ����
            if (mAttribute.contains(attributeName)){
                val attributeValue = attr.getAttributeValue(i)
                //  xml д������ɫ���ܻ��� #
                if (attributeValue.startsWith("#")){
                    return
                }
                var resId : Int
                // ����ͷ�ı�ʾϵͳ��Դ
                if (attributeValue.startsWith("?")){
                    // ��ȡid
                    val attrId = attributeValue.substring(1).toInt()
                    resId = getResId(view.context, IntArray(attrId))[0]
                }else {
                    // ʣ�µ� ���� @ ��ͷ�� �����Լ�����Դ
                    resId = attributeValue.substring(1).toInt()
                }
                mSkinPairs.add(SkinPair(attributeName, resId))
            }
        }
        // �������Ҫ���������� ���� ʵ���� SkinViewSupport �ӿڶ���ʾ��Ҫ����
        if (mSkinPairs.isNotEmpty() || view is SkinViewSupport){
            val skinView = SkinView(view, mSkinPairs)
            //  ���ѡ�񻻷� �ȵ���һ�� ����Ƥ������Դ
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    /**
     * ������ View ���л���
     */
    fun applySkin(){
        mSkinViews.forEach {
            it.applySkin()
        }
    }
}

/**
 * Ҫ������View
 * View ��Ҫ���������Ժ���Դid����
 */
class SkinView(var view : View , var mSkinPairs : MutableList<SkinPair> = mutableListOf()) : SkinViewSupport{
    override fun applySkin() {
        mSkinPairs.forEach {
            applySkinSupport()
            val attributeName = it.attributeName
        }
    }

    private fun applySkinSupport(){
        if (view is SkinViewSupport){
            (view as SkinViewSupport).applySkin()
        }
    }
}

/**
 * Ҫ���������Լ���
 * ������-��Դid
 */
data class SkinPair(var attributeName : String, var resId : Int)

