package com.chencc.androidstudynotescode.framework.pkms

import android.content.pm.PackageInfo
import com.chencc.androidstudynotescode.MyIPackageManager

class MyPackageManagerService  : MyIPackageManager.Stub(){
    override fun getPackageInfo(packageName: String?, flags: Int, userId: Int): PackageInfo? {
        return null
    }

    override fun getPackageInfo2(): String? {
        return "CCC"
    }
}