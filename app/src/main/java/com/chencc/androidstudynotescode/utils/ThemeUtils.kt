package com.chencc.androidstudynotescode.utils

import android.content.Context
import android.util.Log

/**
 * @author Created by CHEN on 2020/8/24
 * @email 188669@163.com
 */

/**
 * 获得 Android SDK 内置 theme 资源 id
 */
fun getResId(context : Context, attrs : IntArray) : IntArray{
    var resIds = IntArray(attrs.size)
    val a = context.obtainStyledAttributes(attrs)
    attrs.forEachIndexed{ index,i ->
        resIds[index] = a.getResourceId(index, 0)
    }
    a.recycle()
    return resIds
}