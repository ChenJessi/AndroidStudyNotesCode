package com.chencc.androidstudynotescode.utils

/**
 * JNI工具类
 */
object JNIUtils {
    init {
        System.loadLibrary("native-lib")
    }

    external fun writeTest()
    external fun readTest()

    /**
     * 热更新
     */
    external fun patch(
        oldApk: String,
        newApk: String,
        patchFile: String
    ) : Int
}