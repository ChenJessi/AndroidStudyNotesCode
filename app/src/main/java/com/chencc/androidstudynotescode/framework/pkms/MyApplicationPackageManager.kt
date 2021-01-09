package com.chencc.androidstudynotescode.framework.pkms

import android.content.pm.PackageInfo
import android.os.RemoteException
import com.chencc.androidstudynotescode.MyIPackageManager

class MyApplicationPackageManager : MyPackageManager() {

    private var mPM : MyIPackageManager? = null
//    MyIPackageManager.Stub.asInterface()
    override fun getPackageInfo(packageName: String?, flags: Int): PackageInfo? {
        return try {
            mPM?.getPackageInfo(packageName, flags, 0)
        } catch (e: RemoteException) {
            e.printStackTrace()
            null
        }
    }

    override fun getPackageInfo2(): String? {
        return try {
            mPM?.packageInfo2
        } catch (e: RemoteException) {
            e.printStackTrace()
            null
        }
    }
}