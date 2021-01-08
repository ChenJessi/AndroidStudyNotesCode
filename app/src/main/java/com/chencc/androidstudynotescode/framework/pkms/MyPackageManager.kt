package com.chencc.androidstudynotescode.framework.pkms

import android.content.pm.PackageInfo



abstract class MyPackageManager {
    abstract fun getPackageInfo(packageName: String?, flags: Int): PackageInfo?

    abstract fun getPackageInfo2(): String?
}