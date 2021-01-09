package com.chencc.androidstudynotescode.framework.pkms

import java.util.concurrent.RecursiveTask

class MyContext {
    fun getPackageManager() : MyPackageManager{
        return MyApplicationPackageManager()
    }
}