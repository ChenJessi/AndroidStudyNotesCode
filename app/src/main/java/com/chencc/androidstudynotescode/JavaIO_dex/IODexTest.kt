package com.chencc.androidstudynotescode.JavaIO_dex

import android.util.Log
import com.chencc.androidstudynotescode.utils.AESUtils
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

        Zip.unZip(File("app/src/main/source/test.apk"), File("app/src/main/source/apk"))
//    Zip.zip(File("app/src/main/test"), File("app/src/main/source/test.apk"))


//
//    var tempFileApk =  File("source/apk/temp")
//    if (tempFileApk.exists()){
//        var files = tempFileApk.listFiles()
//        for (file in files){
//            if (file.isFile){
//                file.delete()
//            }
//        }
//    }
//
//    var tempFileAar =  File("source/aar/temp")
//    if (tempFileAar.exists()){
//        var files = tempFileAar.listFiles()
//        for (file in files){
//            if (file.isFile){
//                file.delete()
//            }
//        }
//    }
//
//    /**
//     * 第一部，处理原始apk  加密dex
//     */
//    AESUtils.init(AESUtils.DEFAULT_PWD)
//    //解压 apk
//    var apkFile = File("source/apk/")
//    var newApkFile = File(apkFile.parent + File.separator + "temp")
//    if (newApkFile.exists()){
//        newApkFile.mkdirs()
//    }













//    Zip.zip( File("app/src/main/test"), File("app/src/main/source/test.apk"))


}