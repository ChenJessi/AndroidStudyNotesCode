package com.chencc.androidstudynotescode.skin

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat

/**
 * @author Created by CHEN on 2020/8/25
 * @email 188669@163.com
 *
 * 皮肤resource信息
 */
object SkinResources {
    // 皮肤包名
    private var mSkinPkgName = ""
    // 是否是默认皮肤
    private var isDefaultSkin = true
    // APP Resource
    private var mAppResource : Resources? = null
    // Skin Resource
    private var mSkinResource : Resources? = null

    /**
     * 初始化 app 的resource
     */
    fun init(context : Context){
        mAppResource = context.resources
    }

    /**
     * 使用皮肤
     */
    fun applySkin(resources: Resources, pkgName : String){
        mSkinResource = resources
        mSkinPkgName = pkgName
        isDefaultSkin = mSkinPkgName.isEmpty() || mSkinResource == null
    }

    /**
     * 重置皮肤信息
     */
    fun reset(){
        mSkinResource = null
        mSkinPkgName = ""
        isDefaultSkin = true
    }
    /**
     * 通过 resId 查找资源
     * 通过 resId 获取到资源 名字以及类型
     * 通过名字和类型从皮肤包中查找 Id
     */
    fun getIdentifier(resId : Int) : Int{
        if (isDefaultSkin){
            return resId
        }
        val resEntryName = mAppResource?.getResourceEntryName(resId)
        val resTypeName = mAppResource?.getResourceTypeName(resId)

        val resId = mSkinResource?.getIdentifier(resEntryName, resTypeName, mSkinPkgName) ?: 0
        return resId
    }

    /**
     * 输入 resId  获取对应的颜色值
     */
    fun getColor(resId : Int) : Int{
        if (isDefaultSkin){
            return mAppResource?.getColor(resId) ?: 0
        }
        val skinId = getIdentifier(resId)
        if (skinId == 0){
            return mAppResource?.getColor(resId) ?: 0
        }
        return mSkinResource?.getColor(skinId) ?: 0
    }
}