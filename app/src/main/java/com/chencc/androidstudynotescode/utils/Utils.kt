package com.chencc.androidstudynotescode.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import java.io.File
import java.io.RandomAccessFile


    fun getBytes(dexFile : File) : ByteArray{
        var fis = RandomAccessFile(dexFile, "r")
        var buffer = ByteArray(fis.length().toInt())
        fis.readFully(buffer)
        fis.close()

        return buffer
    }


