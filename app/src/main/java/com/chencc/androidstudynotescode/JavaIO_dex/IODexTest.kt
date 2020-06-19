package com.chencc.androidstudynotescode.JavaIO_dex

import android.util.Log
import com.chencc.androidstudynotescode.utils.AESUtils
import com.chencc.androidstudynotescode.utils.Dx
import com.chencc.androidstudynotescode.utils.DxJ
import com.chencc.androidstudynotescode.utils.Zip
import org.jetbrains.annotations.TestOnly
import java.io.File
import java.io.FileOutputStream
import java.util.zip.CRC32
import java.util.zip.CheckedOutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream

//@RunWith(AndroidJUnit4::class)
/**
 * Java-IO-dex
 */
fun main() {
    var TAG = "Java-IO-dex"

//        Zip.unZip(File("app/src/main/source/app-release.apk"), File("app/src/main/source/apk"))
//    Zip.zip(File("app/src/main/test"), File("app/src/main/source/test.apk"))



//    val tempFileApk =  File("app/src/main/source/apk/temp")
//    if (tempFileApk.exists()){
//        val files = tempFileApk.listFiles()
//        files?.forEach {
//            if (it.isFile){
//                it.delete()
//            }
//        }
//    }
//
//    var tempFileAar =  File("app/src/main/source/aar/temp")
//    if (tempFileAar.exists()){
//        val files = tempFileAar.listFiles()
//        files?.forEach {
//            if (it.isFile){
//                it.delete()
//            }
//        }
//    }
//
//    /**
//     * 第一步，处理原始apk  加密dex
//     */
//    AESUtils.init(AESUtils.DEFAULT_PWD)
//    //解压 apk
//    val apkFile = File("app/src/main/source/app-release.apk")
//    val newApkFile = File(apkFile.parent + File.separator + "temp")
//
//    if (newApkFile.exists()){
//        newApkFile.mkdirs()
//    }
//    //加密后的dex
//    var mainDexFile = AESUtils.encryptAPKFile(apkFile, newApkFile)
//
//    if (newApkFile.isDirectory){
//        val listFiles = newApkFile.listFiles()
//
//        listFiles?.forEach { file ->
//            if (file.isFile && file.name.endsWith(".dex")){
//                var name = file.name
//                println("file name : $name")
//
//                //重命名
//                var cursor = name.indexOf(".dex")
//                var newName = file.parent + File.separator + name.subSequence(0, cursor) + "_" + ".dex"
//
//                println("file  newName : $newName")
//
//                file.renameTo(File(newName))
//            }
//        }
//    }

    /**
     * 第二步 处理 aar  获得壳 dex
     */
    var aarFile = File("app/src/main/source/mylibrary-release.aar")
    Dx.jar2Dex(aarFile)
//    DxJ.jar2Dex(aarFile)












//    Zip.zip( File("app/src/main/test"), File("app/src/main/source/test.apk"))


}