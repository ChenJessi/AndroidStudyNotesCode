package com.chencc.androidstudynotescode.utils

import android.content.Context
import android.util.Log

/**
 * @author Created by CHEN on 2020/8/24
 * @email 188669@163.com
 */

private const val TAG = "ThemeUtils"


/**
 * ��� Android SDK ���� theme ��Դ id
 */

fun getResId(context : Context, attrs : IntArray) : IntArray{
    var resIds = IntArray(attrs.size)
    val a = context.obtainStyledAttributes(attrs)

    attrs.forEachIndexed{ index,i ->
        Log.e(TAG, "getResId:   ${a}")
        resIds[index] = a.getResourceId(index, 0)
    }
    a.recycle()
    Log.e(TAG, "getResId   resIds:   ${resIds[0]}")
    return resIds
}