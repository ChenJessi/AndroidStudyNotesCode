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
            // 还原默认皮肤
            SkinResources.reset()
        }else{
            try {// 获取宿主 resource
                val appResource = mContext.resources

                val assetManager = AssetManager::class.java.newInstance()
                // 通过反射 设置资源路径
                val method = assetManager.javaClass.getMethod("addAssetPath", String::class.java)
                method.invoke(assetManager, skinPath)

                //  创建 resource
                val skinResource = Resources(assetManager, appResource.displayMetrics, appResource.configuration)

                // 获取外部皮肤包名
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