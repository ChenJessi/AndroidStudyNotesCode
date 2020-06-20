package com.chencc.androidstudynotescode.utils

import java.io.File

object Signature {

    fun signature(unsignedApk : File, signedApk : File){
        val cmd = arrayOf(
            "cmd.exe", "/C ", "jarsigner", "-sigalg", "MD5withRSA",
            "-digestalg", "SHA1",
            "-keystore", "E:/project/pp/git/AndroidStudyNotesCode/notesCode.jks ",
            "-storepass", "111111",
            "-keypass", "111111",
            "-signedjar", signedApk.absolutePath,
            unsignedApk.absolutePath,
            "androiddebugkey"
        )

        val process = Runtime.getRuntime().exec(cmd)
    }


} 