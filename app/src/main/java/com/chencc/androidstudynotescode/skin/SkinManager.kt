package com.chencc.androidstudynotescode.skin

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import java.util.*

object SkinManager : Observable(){
    private lateinit var mContext : Application
    private val skinActivityLifecycle by lazy (){ ApplicationActivityLifecycle(this) }


    fun init(context : Application){
        mContext = context
        SkinPreference.init(mContext)
        // 资源管理类  从 APP/Skin 中加载皮肤
        SkinResources.init(mContext)
        // 注册 activity的生命周期 并设置为被观察者
        mContext.registerActivityLifecycleCallbacks(skinActivityLifecycle)

        loadSkin(SkinPreference.getSkin())
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
        //  通知采集的 View 更新皮肤
        //  被观察者改变 通知所有观察者
        setChanged()
        notifyObservers(null)
    }
}