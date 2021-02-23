package com.chencc.androidstudynotescode.utils

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.view.View
import com.qmuiteam.qmui.util.QMUIDrawableHelper
import java.io.*
import kotlin.math.round

/**
 * BitmapUtils 常用操作
 * 1. 获取 Bitmap 对象
 * 2. Bitmap | Drawable | InputStream | Byte[] 之间进行转换
 */

private const val TAG = "BitmapUtils"

/** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> 读取 Bitmap >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>**/
/**
 * 从本地文件读取 Bitmap, 获取缩放后的本地图片
 * @param filePath 文件路径
 * @param width 宽
 * @param height 高
 * @return
 */
fun readBitmapFromFile(filePath : String, width : Int = 0, height : Int = 0) : Bitmap? {
    if (width == 0 || height == 0){
        return BitmapFactory.decodeFile(filePath)
    }
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
fun  readBitmapFromFileDescriptor(filePath : String, width: Int = 0, height: Int = 0) : Bitmap?{
    try {
        val fis = FileInputStream(filePath)
        if (width == 0 || height == 0){
            return BitmapFactory.decodeFileDescriptor(fis.fd)
        }

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
 *  流只能读取一次，若要获得给定大小的bitmap ，建议使用[inputStreamToBytes]方法
 *
 * @param ins 输入流
 * @param width 宽
 * @param height 高
 * @return
 */
fun readBitmapFromInputStream(ins : InputStream) : Bitmap? {
    return BitmapFactory.decodeStream(ins)
}

/**
 *  从 Resources 资源加载  获取缩放后的本地图片
 *
 *  建议使用 [readBitmapFromResource] 此方法加载
 *  注意 ：  BitmapFactory.decodeResource 加载的图片可能会经过缩放，该缩放目前是放在 java 层做的，效率
 *          比较低，而且需要消耗 java 层的内存。因此，如果大量使用该接口加载图片，容易导致OOM错误
 *          BitmapFactory.decodeByteArray 不会对所加载的图片进行缩放，相比之下占用内存少，效率更高。
 *          这两个接口各有用处，如果对性能要求较高，则应该使用 decodeByteArray ；如果对性能要求不高，且需
 *          要 Android 自带的图片自适应缩放功能，则可以使用 decodeResource 。
 */

fun readBitmapFromResource1(resources: Resources, resourcesId : Int, width: Int = 0, height: Int = 0) : Bitmap? {
    if (width == 0 || height == 0){
        return BitmapFactory.decodeResource(resources, resourcesId)
    }
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
 *          BitmapFactory.decodeByteArray 不会对所加载的图片进行缩放，相比之下占用内存少，效率更高。
 *          这两个接口各有用处，如果对性能要求较高，则应该使用 decodeByteArray ；如果对性能要求不高，且需
 *          要 Android 自带的图片自适应缩放功能，则可以使用 decodeResource 。
 */
fun readBitmapFromResource(resources: Resources, resourcesId : Int, width: Int = 0, height: Int = 0) : Bitmap? {
    val ins = resources.openRawResource(resourcesId)
    return readBitmapFromByteArray(inputStreamToBytes(ins), width, height)
}


/**
 * 获取缩放后的本地图片
 *
 * @param filePath 文件路径， 即文件名称
 */
fun readBitmapFromAssetsFile(context: Context, filePath: String) : Bitmap? {
    var bitmap :Bitmap? = null
    val am = context.assets
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
fun readBitmapFromByteArray(data : ByteArray, width : Int = 0, height: Int = 0) : Bitmap? {
    if (width == 0 || height == 0){
        return BitmapFactory.decodeByteArray(data, 0, data.size)
    }

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

/** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> Bitmap | Drawable | InputStream | Byte[] 之间进行转换 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>**/


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
fun inputStreamToBitmap(ins : InputStream) : Bitmap? {
//    通过resources 获取 inputStream
//    val ins: InputStream = getResources().openRawResource(resourcesId)
    return BitmapFactory.decodeStream(ins)
}

fun InputStream.toBitmap() : Bitmap {
    return BitmapFactory.decodeStream(this)
}

/**
 * inputStream 转 bytes
 */
fun inputStreamToBytes(ins: InputStream) : ByteArray{
//    InputStream is = getResources().openRawResource(resourcesId)
    val baos = ByteArrayOutputStream()
    val b = ByteArray(1024 * 2)
    var len = 0
    while (ins.read(b, 0, b.size).also { len = it } != -1) {
        baos.write(b, 0, len)
        baos.flush()
    }

    return baos.toByteArray()
}



/** >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  Bitmap常见操作 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>**/

/**
 *  Bitmap 保存为本地文件
 */
fun writeBitmapToFile(filePath: String, bitmap: Bitmap, quality : Int = 100){
    try {
        val desFile = File(filePath)
        val fos = FileOutputStream(desFile)
        val bos = BufferedOutputStream(fos)
        bitmap.compress(Bitmap.CompressFormat.PNG, quality, bos)
        bos.flush()
        bos.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

/**
 * 压缩图片
 * @param quality  压缩质量
 */
fun Bitmap.compress(quality : Int = 50) : Bitmap? {
    var  baos : ByteArrayOutputStream? = null
    try {
        baos = ByteArrayOutputStream()
        compress(Bitmap.CompressFormat.PNG, quality, baos)
        val bytes = baos.toByteArray()
        val bais = ByteArrayInputStream(bytes)
        return BitmapFactory.decodeStream(bais)
    }catch (e : OutOfMemoryError){
        e.printStackTrace()
    }finally {
        try {
            baos?.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    return null
}


/**
 * 图片缩放
 */
fun Bitmap.toScale(scale :Float) : Bitmap {
    val matrix = Matrix().apply {
        postScale(scale, scale)     //  宽高等比例缩放
    }
    return Bitmap.createBitmap(this, 0, 0, width, height, matrix, true)
}


/**
 *  获取图片旋转角度
 */
fun readPictureDegree(path : String) : Int {
    if (path.isNullOrEmpty()){
        return 0
    }
    var degree = 0
    try {
        val exifInterface = ExifInterface(path)
        val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        degree = when(orientation){
            ExifInterface.ORIENTATION_ROTATE_90 -> { 90 }
            ExifInterface.ORIENTATION_ROTATE_180 -> { 180 }
            ExifInterface.ORIENTATION_ROTATE_270 -> { 270 }
            else -> { 0 }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return degree
}

/**
 * bitmap 旋转
 */
fun Bitmap.toRotate(rotateDegree : Float = 0f) : Bitmap{
    val matrix = Matrix().apply {
        postRotate(rotateDegree)
    }

    return Bitmap.createBitmap(this, 0, 0,width, height, matrix, true)
}

/**
 * assets 获取 drawable
 * @param filePath 文件路径， 即文件名称
 */
fun readDrawableFromAssetsFile(context: Context, filePath: String) : Drawable? {
    var drawable : Drawable? = null
    try {
        val ins = context.assets.open(filePath)
        drawable = Drawable.createFromStream(ins, null)
        ins.close()
    } catch (e: IOException) {
        e.printStackTrace()
    }
    return drawable
}

/**
 * View 转 Bitmap
 * 将View中内容绘制到Bitmap上
 */
fun View.toBitmap() : Bitmap {
    clearFocus()
    val returnedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(returnedBitmap)
    if (background != null){
        background.draw(canvas)
    }else{
        canvas.drawColor(Color.WHITE)
    }
    draw(canvas)
    return returnedBitmap
}

/**
 * View 转 Bitmap
 * 从 View 缓存 Cache 中获取
 */
fun convertViewToBitmap(view: View){
    view.apply {
        isDrawingCacheEnabled = true
    }
}

