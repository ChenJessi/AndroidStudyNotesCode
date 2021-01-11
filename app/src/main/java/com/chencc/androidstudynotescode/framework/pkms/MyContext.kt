package com.chencc.androidstudynotescode.framework.pkms

import java.util.concurrent.RecursiveTask

class MyContext {


    fun test(){
        val myContext =  MyContext()
        myContext.getPackageManager().getPackageInfo("com.chen.androidstudynotescode", 0)
        val string = myContext.getPackageManager().getPackageInfo2()
    }

    fun getPackageManager() : MyPackageManager{
        return MyApplicationPackageManager()
    }
}