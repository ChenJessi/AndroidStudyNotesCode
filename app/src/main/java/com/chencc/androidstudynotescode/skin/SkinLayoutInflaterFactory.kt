package com.chencc.androidstudynotescode.skin

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View

/**
 * @author Created by CHEN on 2020/8/27
 * @email 188669@163.com
 */
class SkinLayoutInflaterFactory(var activity : Activity) : LayoutInflater.Factory2 {

    // 需要查找替换view 的属性
    val skinAttribute = SkinAttribute()

    override fun onCreateView(parent: View?, name: String, context: Context, attrs: AttributeSet): View? {
        TODO("Not yet implemented")
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        TODO("Not yet implemented")
    }
}