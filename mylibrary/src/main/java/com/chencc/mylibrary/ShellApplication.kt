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
     * dex ����
     */
    fun IODexTest() {

        init(DEFAULT_PWD)

        var apkFile = File(applicationInfo.sourceDir)
        // /data/app/com.chencc.androidstudynotescode-z6lKjUP-NRQVFvs6yxbrbg==/base.apk
        println("sourceDir �� ${applicationInfo.sourceDir}")

        //data/data/����/files/fake_apk/
        // �����ļ��� ��� apk ��ѹ�ļ�
        var unZipFile = getDir("fake_apk", Context.MODE_PRIVATE)
        var app = File(unZipFile, "app")
        // ���ļ����ڣ����Ѿ����ܹ� dex �ˣ�ֱ�Ӽ��ؼ���
        if (!app.exists()) {
            unZip(apkFile, app)
            var files = app.listFiles()
            // �� dex �� classes.dex
            // �� �� ��dex ���н���
            files.forEach {
                var name = it.name
                when {
                    name == "classes.dex" -> {
                        // �� dex  �������
                    }
                    name.endsWith(".dex") -> {
                        try {
                            /**
                             *  ���� dex �ļ�
                             *  �������ܺ����������д���ļ�
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