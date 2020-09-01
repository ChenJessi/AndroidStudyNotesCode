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
        "drawableBottom"
    )
    private val mSkinViews = mutableListOf<SkinView>()

    /**
     * 遍历每个 view 的所有属性
     * 找出需要换肤的属性并记录 属性名称 和 资源id
     */
    fun look(view: View, attr: AttributeSet) {
        val mSkinPairs = mutableListOf<SkinPair>()
        for (i in 0 until attr.attributeCount) {
            // 获取属性名
            val attributeName = attr.getAttributeName(i)
            // 有集合中的属性才需要换肤
            if (mAttribute.contains(attributeName)) {
                val attributeValue = attr.getAttributeValue(i)
                //  xml 写死的颜色不能换肤 #
                if (attributeValue.startsWith("#")) {
                    return
                }
                var resId: Int
                // ？开头的表示系统资源
                if (attributeValue.startsWith("?")) {
                    // 获取id
                    val attrId = attributeValue.substring(1).toInt()
                    resId = getResId(view.context, IntArray(1) { attrId })[0]
                } else {
                    // 剩下的 是以 @ 开头的 我们自己的资源
                    resId = attributeValue.substring(1).toInt()
                    Log.e("TAG", "look:  $resId   ${attributeValue}")

                }
                mSkinPairs.add(SkinPair(attributeName, resId))
            }
        }
        // 如果有需要换肤的属性 或者 实现了 SkinViewSupport 接口都表示需要换肤
        if (mSkinPairs.isNotEmpty() || view is SkinViewSupport) {
            val skinView = SkinView(view, mSkinPairs)
            //  如果选择换肤 先调用一次 加载皮肤的资源
            skinView.applySkin()
            mSkinViews.add(skinView)
        }
    }

    /**
     * 对所有 View 进行换肤
     */
    fun applySkin() {
        mSkinViews.forEach {
            it.applySkin()
        }
    }
}

/**
 * 要换肤的View
 * View 需要更换的属性和资源id集合
 */
class SkinView(var view: View, var mSkinPairs: MutableList<SkinPair> = mutableListOf()) :
    SkinViewSupport {

    /**遍历所有需要修改的属性
     *
     * 对view的每一个属性进行修改
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
                    // background 为 color 时 为 int 类型
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
 * 修改实现了 SkinViewSupport 的view属性
 */
private fun applySkinSupport() {
    if (view is SkinViewSupport) {
        (view as SkinViewSupport).applySkin()
    }
}
}

/**
 * 要换肤的属性集合
 * 属性名-资源id
 */
data class SkinPair(var attributeName: String, var resId: Int)

