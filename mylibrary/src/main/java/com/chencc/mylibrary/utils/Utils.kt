package com.chencc.mylibrary.utils

import java.io.File
import java.io.RandomAccessFile



    fun getBytes(dexFile : File) : ByteArray{
        var fis = RandomAccessFile(dexFile, "r")
        var buffer = ByteArray(fis.length().toInt())
        fis.readFully(buffer)
        fis.close()
        return buffer
    }
