package com.chencc.androidstudynotescode.skin

import android.util.AttributeSet
import android.view.View
import com.chencc.androidstudynotescode.utils.getResId

/**
 * @author Created by CHEN on 2020/8/24
 * @email 188669@163.com
 */

/**
 * 要换肤的 View 的属性
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
     * 记录每个view 需要换肤的属性
     */
    fun look(view: View, attr : AttributeSet){
        val mSkinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attr.attributeCount){
            // 获取属性名
            val attributeName = attr.getAttributeName(i)
            // 有集合中的属性才需要换肤
            if (mAttribute.contains(attributeName)){
                val attributeValue = attr.getAttributeValue(i)
                //  xml 写死的颜色不能换肤 #
                if (attributeValue.startsWith("#")){
                    return
                }
                var resId : Int
                // ？开头的表示系统资源
                if (attributeValue.startsWith("?")){
                    // 获取id
                    val attrId = attributeValue.substring(1).toInt()
                    resId = getResId(view.context, IntArray(attrId))[0]
                }else {
                    // 剩下的 是以 @ 开头的 我们自己的资源
                    resId = attributeValue.substring(1).toInt()
                }
                mSkinPairs.add(SkinPair(attributeName, resId))
            }
        }
        // 如果有需要换肤的属性 或者 实现了 SkinViewSupport 接口都表示需要换肤
        if (mSkinPairs.isNotEmpty() || view is SkinViewSupport){
            val skinView = SkinView(view, mSkinPairs)
            //  如果选择换肤 先调用一次 加载皮肤的资源
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    /**
     * 对所有 View 进行换肤
     */
    fun applySkin(){
        mSkinViews.forEach {
            it.applySkin()
        }
    }
}

/**
 * 要换肤的View
 * View 需要更换的属性和资源id集合
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
 * 要换肤的属性集合
 * 属性名-资源id
 */
data class SkinPair(var attributeName : String, var resId : Int)

