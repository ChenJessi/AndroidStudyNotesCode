package com.chencc.androidstudynotescode.skin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources

class SkinManager {
    private lateinit var mContext : Context
    fun init(context : Context){
        mContext = context
        SkinResources.init(mContext)
        loadSkin("")
    }


    fun loadSkin(skinPath : String){
        if (skinPath.isEmpty()){
            // ��ԭĬ��Ƥ��
            SkinResources.reset()
        }else{
            try {// ��ȡ���� resource
                val appResource = mContext.resources

                val assetManager = AssetManager::class.java.newInstance()
                // ͨ������ ������Դ·��
                val method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                method.invoke(assetManager, skinPath)

                //  ���� resource
                val skinResource = Resources(assetManager, appResource.displayMetrics, appResource.configuration)

                // ��ȡ�ⲿƤ������
                val packageManager = mContext.packageManager

                val packageArchiveInfo =
                    packageManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                val skinPkgName = packageArchiveInfo.packageName
                SkinResources.applySkin(skinResource , skinPkgName)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }
}