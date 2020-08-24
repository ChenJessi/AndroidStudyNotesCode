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
    private val mSkinViews = mutableListOf<SkinViewSupport>()

    /**
     * ��¼ÿ��view ��Ҫ����������
     */
    fun look(view: View, attr : AttributeSet){
        val mSkinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attr.attributeCount){
            // ��ȡ������
            val attributeName = attr.getAttributeName(i)
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
                }
            }
        }

    }

}

/**
 * Ҫ������View
 * View ��Ҫ���������Ժ���Դid����
 */
class SkinView(var view : View , mSkinPairs : MutableList<SkinPair> = mutableListOf()) : SkinViewSupport{
    override fun applySkin() {

    }
}

/**
 * Ҫ���������Լ���
 * ������-��Դid
 */
data class SkinPair(var attributeName : String, var resId : Int)

