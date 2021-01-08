package com.chencc.androidstudynotescode.framework.pkms

import android.content.pm.PackageInfo

class MyApplicationPackageManager : MyPackageManager() {
    override fun getPackageInfo(packageName: String?, flags: Int): PackageInfo? {
        TODO("Not yet implemented")
    }

    override fun getPackageInfo2(): String? {
        TODO("Not yet implemented")
    }
}