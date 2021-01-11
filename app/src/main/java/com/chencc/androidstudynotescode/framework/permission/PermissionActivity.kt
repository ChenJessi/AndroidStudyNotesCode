package com.chencc.androidstudynotescode.framework.permission

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.IPackageInstallObserver
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import java.io.File


/**
 * 静默安装测试用例
 * 实际业务场景中，因为无法拿到系统签名不可用
 */
private const val TAG = "PermissionActivity"
class PermissionActivity : AppCompatActivity(){

    private lateinit var receiver : BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /**
         * Apk 安装监听
         */
         receiver = MyInstallReceiver().apply {
            val filter = IntentFilter().apply {
                addAction("android.intent.action.PACKAGE_ADDED")
                addAction("android.intent.action.PACKAGE_REMOVED")
                addDataScheme("package")
            }
            registerReceiver(this, filter)
        }


    }

    /**
     * 需要增加三个 aidl  PackageManager等系统源码才行
     * 这是直接调用PKMS.installPackage方法安装
     *  这种安装的方式，都需要在AndroidManifest.xml中 声明 android:sharedUserId="android.uid.system"
     */
    private fun install(){
//        val fileName = "${Environment.getExternalStorageDirectory()}${File.separator}${File.separator}wms.apk"
//        val uri = Uri.fromFile(File(fileName))
//        var installFlags = 0
//        val pm = packageManager
//
//        try {
//            val pi = pm.getPackageInfo("com.chencc.androidstudynotescode", PackageManager.GET_UNINSTALLED_PACKAGES)?.apply {
//                installFlags =
//                    installFlags or PackageManager.INSTALL_REPLACE_EXISTING
//            }
//        } catch (e: PackageManager.NameNotFoundException) {
//        }
//        val observer = MyPakcageInstallObserver()
//         // android 6.0 方法
//        pm.installPackage(uri, observer, installFlags, "com.chencc.androidstudynotescode");
//        // android 10.0 方法
//        pm.packageInstaller.installExistingPackage("com.chencc.androidstudynotescode", "安装", null)
    }

    /**
     * 静默安装
     * 需要增加三个 aidl  PackageManager等系统源码才行
     * 这是直接调用PKMS.installPackage方法安装
     *  这种安装的方式，都需要在AndroidManifest.xml中 声明 android:sharedUserId="android.uid.system"
     */
    private fun silentInstall(pkgName : String , apkAbsolutePath :String) : Boolean {
        var isSuccess = false
//        val args = arrayOf(
//            "pm",
//            "install",
//            "-r",
//            "-d",
//            "-i",
//            pkgName,
//            "--user",
//            "0",
//            apkAbsolutePath
//        )
//        val processBuilder = ProcessBuilder(*args)
//        var process : Process? = null
//        var inIs : InputStream? = null
//
//        try {
//            val baos = ByteArrayOutputStream()
//            process = processBuilder.start()
//            baos.write('/')
//            inIs = process.inputStream
//            var b = ByteArray(1024)
//            while (inIs.read(b) != -1) {
//                baos.write(b)
//            }
//            val res = String(baos.toByteArray(), Charset.forName("utf-8"))
//            isSuccess = res.contains("Success")
//            baos.close()
//        } catch (e: Exception) {
//            Log.i(TAG, "silentInstall end$e")
//            e.printStackTrace()
//        } finally {
//            try {
//                inIs?.close()
//            } catch (e: java.lang.Exception) {
//                e.printStackTrace()
//            }
//            process?.destroy()
//        }
//
//        Log.i(TAG, "silentInstall end isSuccess$isSuccess")
        return isSuccess
    }


    /**
     * 智能安装
     *什么是智能安装？ 就是通过 AccessibilityService辅助服务 来帮我们自动点击 确定 下一步 下一步 安装完成
     * 现在很多手机都需要密码，所以只能安装也无法实现
     */
    private fun smartInstall(){
//        val fileName = "${Environment.getExternalStorageDirectory()}${File.separator}${File.separator}wms.apk"
//        val uri: Uri = Uri.fromFile(File(fileName))
//        val localIntent = Intent(Intent.ACTION_VIEW)
//        localIntent.setDataAndType(uri, "application/vnd.android.package-archive")
//        startActivity(localIntent)
    }




    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    inner class MyInstallReceiver : BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            when(intent?.action){
                "android.intent.action.PACKAGE_ADDED" -> {
                    Log.i(TAG, "onReceive:    安装了  ${intent.dataString}")
                }
                "android.intent.action.PACKAGE_REMOVED" -> {
                    Log.i(TAG, "onReceive:    卸载了  ${intent.dataString}")
                }
            }
        }
    }

    inner class MyPakcageInstallObserver : IPackageInstallObserver.Stub(){
        override fun packageInstalled(packageName: String?, returnCode: Int) {
            if (returnCode == 1){
                Log.e(TAG, "安装成功")
            }else{
                Log.e(TAG, "安装失败, 返回码：$returnCode")
            }
        }

    }
}


