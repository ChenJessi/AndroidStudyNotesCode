package com.chencc.androidstudynotescode.utils

import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.RuntimeException
import java.nio.charset.Charset

object Signature {

    fun signature(unsignedApk : File, signedApk : File){
        val cmd = arrayOf(
            "cmd.exe", "/C",  "E:/AndroidStudio/as/jre/bin/jarsigner", "-sigalg", "MD5withRSA",
            "-digestalg", "SHA1",
//            "-keystore", "E:/project/pp/git/AndroidStudyNotesCode/notesCode.jks",
            "-keystore", "C:/Users/Administrator.USER-20200422BX/.android/debug.keystore",
//            "-storepass", "111111",
//            "-keypass", "111111",
            "-storepass", "android",
            "-keypass", "android",
            "-signedjar", "E:/project/pp/git/AndroidStudyNotesCode/app/src/main/source/apk/apk-signed.apk",
                            "E:/project/pp/git/AndroidStudyNotesCode/app/src/main/source/apk/apk-unsigned.apk",
//            "key0"
            "androiddebugkey"
        )

        val process = Runtime.getRuntime().exec(cmd)
        println("start sign")

        Dx.read(process.inputStream, System.out);
        Dx.read(process.errorStream, System.err);

        var waitResult = process.waitFor()
        println("waitResult : $waitResult")

        println("exitValue :  ${process.exitValue()}")
        if (process.exitValue() != 0){
            var inputStream = process.errorStream
            var len = 0
            var buffer = ByteArray(2048)
            var bos = ByteArrayOutputStream()
            while (inputStream.read(buffer).also { len = it } != -1){
                bos.write(buffer, 0, len)
            }
            println(String(bos.toByteArray(),Charset.forName("GBK")))
            throw RuntimeException("签名执行失败")
        }

    }


} 