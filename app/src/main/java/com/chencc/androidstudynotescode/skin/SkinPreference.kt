package com.chencc.androidstudynotescode.skin

import android.content.Context
import android.content.SharedPreferences




/**
 * @author Created by CHEN on 2020/8/28
 * @email 188669@163.com
 */
object SkinPreference {

    private const val SKIN_SHARED = "skins"
    private const val KEY_SKIN_PATH = "skin-path"

    private lateinit var mPref: SharedPreferences

    fun init(context: Context){
        mPref = context.getSharedPreferences(SKIN_SHARED, Context.MODE_PRIVATE)
    }

    fun setSkin(skinPath: String) { mPref.edit()?.putString(KEY_SKIN_PATH, skinPath)?.apply()
    }

    fun reset() {
        mPref.edit()?.remove(KEY_SKIN_PATH)?.apply()
    }

    fun getSkin(): String {
        return mPref.getString(KEY_SKIN_PATH, "")?:""
    }

}