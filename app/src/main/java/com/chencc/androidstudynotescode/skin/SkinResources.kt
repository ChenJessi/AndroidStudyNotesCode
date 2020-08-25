package com.chencc.androidstudynotescode.skin

import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat

/**
 * @author Created by CHEN on 2020/8/25
 * @email 188669@163.com
 *
 * Ƥ��resource��Ϣ
 */
object SkinResources {
    // Ƥ������
    private var mSkinPkgName = ""
    // �Ƿ���Ĭ��Ƥ��
    private var isDefaultSkin = true
    // APP Resource
    private var mAppResource : Resources? = null
    // Skin Resource
    private var mSkinResource : Resources? = null

    /**
     * ��ʼ�� app ��resource
     */
    fun init(context : Context){
        mAppResource = context.resources
    }

    /**
     * ʹ��Ƥ��
     */
    fun applySkin(resources: Resources, pkgName : String){
        mSkinResource = resources
        mSkinPkgName = pkgName
        isDefaultSkin = mSkinPkgName.isEmpty() || mSkinResource == null
    }

    /**
     * ����Ƥ����Ϣ
     */
    fun reset(){
        mSkinResource = null
        mSkinPkgName = ""
        isDefaultSkin = true
    }
    /**
     * ͨ�� resId ������Դ
     * ͨ�� resId ��ȡ����Դ �����Լ�����
     * ͨ�����ֺ����ʹ�Ƥ�����в��� Id
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
     * ���� resId  ��ȡ��Ӧ����ɫֵ
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