// MyIPackageManager.aidl
package com.chencc.androidstudynotescode;

// Declare any non-default types here with import statements

interface MyIPackageManager {

    PackageInfo getPackageInfo(String packageName, int flags, int userId);

    String getPackageInfo2();
}