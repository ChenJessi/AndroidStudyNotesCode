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
        // ��Դ������  �� APP/Skin �м���Ƥ��
        SkinResources.init(mContext)
        // ע�� activity���������� ������Ϊ���۲���
        mContext.registerActivityLifecycleCallbacks(skinActivityLifecycle)

        loadSkin(SkinPreference.getSkin())
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
        //  ֪ͨ�ɼ��� View ����Ƥ��
        //  ���۲��߸ı� ֪ͨ���й۲���
        setChanged()
        notifyObservers(null)
    }
}