package com.chencc.androidstudynotescode.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.TypedValue
import androidx.annotation.DrawableRes
import java.io.File
import java.io.RandomAccessFile


    fun getBytes(dexFile : File) : ByteArray{
        var fis = RandomAccessFile(dexFile, "r")
        var buffer = ByteArray(fis.length().toInt())
        fis.readFully(buffer)
        fis.close()

        return buffer
    }



    fun getBitmap(res: Resources, @DrawableRes id: Int, width: Int) : Bitmap{
        val option = BitmapFactory.Options()
        option.inJustDecodeBounds = true
        BitmapFactory.decodeResource(res, id, option)
        option.inJustDecodeBounds = false
        option.inDensity = option.outWidth
        option.inTargetDensity = width
        return BitmapFactory.decodeResource(res, id, option)
    }