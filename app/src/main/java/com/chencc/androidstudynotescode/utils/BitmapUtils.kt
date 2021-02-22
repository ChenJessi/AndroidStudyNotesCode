package com.chencc.androidstudynotescode.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.PixelFormat
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import kotlin.math.round

/**
 * BitmapUtils 常用操作
 * 1. 获取 Bitmap 对象
 * 2. Bitmap | Drawable | InputStream | Byte[] 之间进行转换
 */


/**
 * 从本地文件读取 Bitmap, 获取缩放后的本地图片
 * @param filePath 文件路径
 * @param width 宽
 * @param height 高
 * @return
 */
fun readBitmapFromFile(filePath : String, width : Int, height : Int) : Bitmap? {
    val options = BitmapFactory.Options().apply {
        // 只返回尺寸，但是不加载返回
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeFile(filePath, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    var inSampleSize = 1
    if (srcHeight > height || srcWidth > width){
        if (srcWidth > srcHeight){
            inSampleSize = round(srcHeight / height).toInt()
        }else{
            inSampleSize = round(srcWidth / width).toInt()
        }
    }
    options.inJustDecodeBounds = false
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeFile(filePath , options)
}

/**
 * 从本地文件读取 Bitmap  获取缩放后的本地图片
 * 效率高于上面 [readBitmapFromFile] 方法
 *
 * @param filePath 文件路径
 * @param width 宽
 * @param height 高
 * @return
 */
fun  readBitmapFromFileDescriptor(filePath : String, width: Int, height: Int) : Bitmap?{
    try {
        val fis = FileInputStream(filePath)
        val options = BitmapFactory.Options().apply {
            // 只返回尺寸，但是不加载返回
           inJustDecodeBounds = true
        }
        BitmapFactory.decodeFileDescriptor(fis.fd, null, options)
        val srcWidth = options.outWidth.toFloat()
        val srcHeight = options.outHeight.toFloat()
        var inSampleSize = 1
        if (srcHeight > height || srcWidth > width){
            if (srcWidth > srcHeight){
                inSampleSize = round(srcHeight / height).toInt()
            }else{
                inSampleSize = round(srcWidth / width).toInt()
            }
        }
        options.inJustDecodeBounds = false
        options.inSampleSize = inSampleSize
        return BitmapFactory.decodeFileDescriptor(fis.fd, null, options)
    } catch (e: Exception) {
    }
    return null
}


/**
 * 从输入流中读取文件 获取缩放后的本地图片
 *
 * @param ins 输入流
 * @param width 宽
 * @param height 高
 * @return
 */
fun readBitmapFromInputStream(ins : InputStream, width: Int, height: Int) : Bitmap? {
    val options = BitmapFactory.Options().apply {
        // 只返回尺寸，但是不加载返回
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeStream(ins, null, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    var inSampleSize = 1
    if (srcHeight > height || srcWidth > width){
        if (srcWidth > srcHeight){
            inSampleSize = round(srcHeight / height).toInt()
        }else{
            inSampleSize = round(srcWidth / width).toInt()
        }
    }
    options.inJustDecodeBounds = false
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeStream(ins, null, options)
}

/**
 *  从 Resources 资源加载  获取缩放后的本地图片
 *
 *  建议使用 [readBitmapFromResource] 此方法加载
 *  注意 ：  BitmapFactory.decodeResource 加载的图片可能会经过缩放，该缩放目前是放在 java 层做的，效率
 *          比较低，而且需要消耗 java 层的内存。因此，如果大量使用该接口加载图片，容易导致OOM错误
 *          BitmapFactory.decodeStream 不会对所加载的图片进行缩放，相比之下占用内存少，效率更高。
 *          这两个接口各有用处，如果对性能要求较高，则应该使用 decodeStream ；如果对性能要求不高，且需
 *          要 Android 自带的图片自适应缩放功能，则可以使用 decodeResource 。
 */

fun readBitmapFromResource1(resources: Resources, resourcesId : Int, width: Int, height: Int) : Bitmap? {
    val ins = resources
    val options = BitmapFactory.Options().apply {
        // 只返回尺寸，但是不加载返回
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeResource(resources, resourcesId, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    var inSampleSize = 1
    if (srcHeight > height || srcWidth > width){
        if (srcWidth > srcHeight){
            inSampleSize = round(srcHeight / height).toInt()
        }else{
            inSampleSize = round(srcWidth / width).toInt()
        }
    }
    options.inJustDecodeBounds = false
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeResource(resources, resourcesId, options)
}

/**
 * 从 Resources 资源加载， 获取缩放后的本地图片
 *
 *  建议使用此方法加载
 *  注意 ：  BitmapFactory.decodeResource 加载的图片可能会经过缩放，该缩放目前是放在 java 层做的，效率
 *          比较低，而且需要消耗 java 层的内存。因此，如果大量使用该接口加载图片，容易导致OOM错误
 *          BitmapFactory.decodeStream 不会对所加载的图片进行缩放，相比之下占用内存少，效率更高。
 *          这两个接口各有用处，如果对性能要求较高，则应该使用 decodeStream ；如果对性能要求不高，且需
 *          要 Android 自带的图片自适应缩放功能，则可以使用 decodeResource 。
 */
fun readBitmapFromResource(resources: Resources, resourcesId : Int, width: Int, height: Int) : Bitmap? {
    val ins = resources.openRawResource(resourcesId)
    return readBitmapFromInputStream(ins, width, height)
}


/**
 * 获取缩放后的本地图片
 *
 * @param filePath 文件路径， 即文件名称
 */
fun readBitmapFromAssetsFile(context: Context, filePath: String) : Bitmap? {
    var bitmap :Bitmap? = null
    val am = context.resources.assets
    try {
        val ins = am.open(filePath)
        bitmap = BitmapFactory.decodeStream(ins)
        ins.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return bitmap
}


/**
 * 从二进制数据读取bitmap
 */
fun readBitmapFromByteArray(data : ByteArray, width : Int, height: Int) : Bitmap? {
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeByteArray(data, 0, data.size, options)
    val srcWidth = options.outWidth.toFloat()
    val srcHeight = options.outHeight.toFloat()
    var inSampleSize = 1
    if (srcHeight > height || srcWidth > width){
        if (srcWidth > srcHeight){
            inSampleSize = round(srcHeight / height).toInt()
        }else{
            inSampleSize = round(srcWidth / width).toInt()
        }
    }
    options.inJustDecodeBounds = false
    options.inSampleSize = inSampleSize
    return BitmapFactory.decodeByteArray(data, 0, data.size, options)
}



/**
 *  Bitmap | Drawable | InputStream | Byte[ ] 之间进行转换
 */

/**
 * Drawable 转 Bitmap
 */
fun Drawable.toBitmap() : Bitmap{
    val config = if (opacity != PixelFormat.OPAQUE)  Bitmap.Config.ARGB_8888 else Bitmap.Config.RGB_565
    val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, config)
    val canvas = Canvas(bitmap)
    setBounds(0,0, intrinsicWidth, intrinsicHeight)
    draw(canvas)
    return bitmap
}

/**
 * Bitmap 转 Drawable
 */
fun Bitmap.toDrawable(resources: Resources) : Drawable {
    return BitmapDrawable(resources, this)
}

/**
 * Bitmap 转 ByteArray
 */
fun Bitmap.toBytes() : ByteArray {
    val baos = ByteArrayOutputStream()
    compress(Bitmap.CompressFormat.PNG, 100, baos)
    return baos.toByteArray()
}

/**
 * byte[] 转换成 Bitmap
 */
fun bytesToBitmap(bytes : ByteArray) : Bitmap {
    val bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    return bitmap
}

/**
 * InputStream 转 Bitmap
 */
//fun inputStreamToBitmap(ins : InputStream) : Bitmap? {
//
//}