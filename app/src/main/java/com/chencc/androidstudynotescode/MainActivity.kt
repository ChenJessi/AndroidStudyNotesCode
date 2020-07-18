package com.chencc.androidstudynotescode

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import android.util.Log
import dalvik.system.DexClassLoader
import dalvik.system.PathClassLoader
import java.util.zip.ZipFile
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("MainActivity", "Activity.class 由： + ${Activity::class.java.classLoader} + 加载" )
        Log.e("MainActivity", "MainActivity.class 由： + $classLoader + 加载" )
    }
}