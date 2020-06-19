package com.chencc.androidstudynotescode.utils

import java.io.File
import java.io.RandomAccessFile

object Utils {


    fun getBytes(dexFile : File) : ByteArray{
        var fis = RandomAccessFile(dexFile, "r")
        var buffer = ByteArray(fis.length().toInt())
        fis.readFully(buffer)
        fis.close()
        return buffer
    }
}