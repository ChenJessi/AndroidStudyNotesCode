package com.chencc.androidstudynotescode.JavaIO_dex

import com.chencc.androidstudynotescode.utils.*
import java.io.File
import java.io.FileOutputStream

//@RunWith(AndroidJUnit4::class)
/**
 * Java-IO-dex
 */
fun main() {
    var TAG = "Java-IO-dex"

//        Zip.unZip(File("app/src/main/source/app-release.apk"), File("app/src/main/source/apk"))
//    Zip.zip(File("app/src/main/test"), File("app/src/main/source/test.apk"))

//    val newApkFile = File("app/src/main/source/temp")
//    var unsignedApk = File("app/src/main/source/apk/apk-unsigned.apk")
//    unsignedApk.parentFile.mkdirs()
//
//    Zip.zip(newApkFile ,unsignedApk)


//    var unsignedApk = File("app/src/main/source/apk/apk-unsigned.apk")
//    unsignedApk.parentFile.mkdirs()
//
//    var signedApk = File("app/src/main/source/apk/apk-signed.apk")
//    Signature.signature(unsignedApk, signedApk)

    val fileApk =  File("app/src/main/source/apk")
    if (fileApk.exists()){
        val files = fileApk.listFiles()
        files?.forEach {
            if (it.isFile){
                it.delete()
            }
        }
    }

    var tempFileAar =  File("app/src/main/source/aar")
    if (tempFileAar.exists()){
        val files = tempFileAar.listFiles()
        files?.forEach {
            if (it.isFile){
                it.delete()
            }
        }
    }
    var tempFile =  File("app/src/main/source/temp")
    if (tempFileAar.exists()){
        val files = tempFileAar.listFiles()
        files?.forEach {
            if (it.isFile){
                it.delete()
            }
        }
    }

    /**
     * 第一步，处理原始apk  加密dex
     */
    AESUtils.init(AESUtils.DEFAULT_PWD)
    //解压 apk
    val apkFile = File("app/src/main/source/app-release.apk")
    val newApkFile = File(apkFile.parent + File.separator + "temp")

    if (newApkFile.exists()){
        newApkFile.mkdirs()
    }
    //加密后的dex
    var mainDexFile = AESUtils.encryptAPKFile(apkFile, newApkFile)

    if (newApkFile.isDirectory){
        val listFiles = newApkFile.listFiles()

        listFiles?.forEachIndexed { index, file  ->
            if (file.isFile && file.name.endsWith(".dex")){
                var name = file.name
                println("file name : $name")

                //对加密后的dex 文件重命名
                var cursor = name.indexOf(".dex")
                var newName = file.parent + File.separator + name.subSequence(0, cursor) + index + ".dex"

                println("file  newName : $newName")

                file.renameTo(File(newName))
            }
        }
    }

    /**
     * 第二步 处理 aar  获得壳 dex
     */
    var aarFile = File("app/src/main/source/mylibrary-release.aar")
    var aarDex = Dx.jar2Dex(aarFile)

//    获取 apk 的dex 文件
    val tempMainDex = File( newApkFile.path.toString() + File.separator + "classes.dex")

    if (!tempMainDex.exists()){
        tempMainDex.createNewFile()
    }
    //将 aarDex 包写入 解压后的 apk 包
    var fos = FileOutputStream(tempMainDex)
    var bytes = getBytes(aarDex)
    fos.write(bytes)
    fos.flush()
    fos.close()

    /**
     *  打包 签名
     */
    var unsignedApk = File("app/src/main/source/apk/apk-unsigned.apk")
    unsignedApk.parentFile.mkdirs()

    Zip.zip(newApkFile ,unsignedApk)

//     对 apk 签名
    var signedApk = File("app/src/main/source/apk/apk-signed.apk")
    Signature.signature(unsignedApk, signedApk)











}