package com.chencc.mylibrary

import android.app.Application
import android.content.Context
import android.util.Log
import com.chencc.mylibrary.utils.*
import java.io.File
import java.io.FileOutputStream
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.collections.ArrayList
class ShellApplication : Application() {
    private val TAG = "ShellApplication"
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        IODexTest()

    }


    /**
     * dex 解密
     */
    fun IODexTest() {

        init(DEFAULT_PWD)

        var apkFile = File(applicationInfo.sourceDir)
        // /data/app/com.chencc.androidstudynotescode-z6lKjUP-NRQVFvs6yxbrbg==/base.apk
        println("sourceDir ： ${applicationInfo.sourceDir}")

        //data/data/包名/files/fake_apk/
        // 创建文件夹 存放 apk 解压文件
        var unZipFile = getDir("fake_apk", Context.MODE_PRIVATE)
        var app = File(unZipFile, "app")
        // 若文件存在，则已经解密过 dex 了，直接加载即可
        if (!app.exists()) {
            unZip(apkFile, app)
            var files = app.listFiles()
            // 壳 dex 即 classes.dex
            // 对 非 壳dex 进行解密
            files.forEach {
                var name = it.name
                when {
                    name == "classes.dex" -> {
                        // 壳 dex  无需解密
                    }
                    name.endsWith(".dex") -> {
                        try {
                            /**
                             *  解密 dex 文件
                             *  并将解密后的数据重新写入文件
                             */
                            var buffer = getBytes(it)
                            var decrypt = decrypt(buffer)
                            var fos = FileOutputStream(it)

                            fos.write(decrypt)
                            fos.flush()
                            fos.close()
                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }
                }
            }
        }

        var list = ArrayList<File>()
        Log.d(TAG, "FAKE" + Arrays.toString(app.listFiles()));
        app.listFiles()?.forEach {
            if (it.name.endsWith(".dex")) {
                list.add(it)
            }
        }

        Log.d(TAG, "IODexTest:  $list")
        try {
            V19.install(classLoader, list, unZipFile)
        } catch (e : IllegalAccessException) {
            e.printStackTrace()
        } catch (e : NoSuchFieldException) {
            e.printStackTrace()
        } catch (e : InvocationTargetException) {
            e.printStackTrace()
        } catch (e : NoSuchMethodException) {
            e.printStackTrace()
        }
    }

}