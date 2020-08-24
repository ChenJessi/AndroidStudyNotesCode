package com.chencc.androidstudynotescode.utils

import android.content.Context

/**
 * @author Created by CHEN on 2020/8/24
 * @email 188669@163.com
 */

/**
 * »ñµÃ theme
 */
fun getResId(context : Context, attrs : IntArray) : IntArray{
    var resId = IntArray(attrs.size)
    val a = context.obtainStyledAttributes(attrs)
    attrs.forEachIndexed { index, i ->
        resId[index] = a.getResourceId(i, 0)
    }
    a.recycle()
    return resId
}