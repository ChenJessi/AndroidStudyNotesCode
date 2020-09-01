package com.chencc.androidstudynotescode.skin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_skin_test.*

/**
 * @author Created by CHEN on 2020/8/31
 * @email 188669@163.com
 */
class SkinTestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_skin_test)

        tvSkin.setOnClickListener {
//            SkinManager.loadSkin("/sdcard/Android/data/skinlib.apk")
//            SkinManager.loadSkin("/sdcard/Android/data/skinlib-debug.aar")
            SkinManager.loadSkin("/sdcard/Android/data/app-debug.apk")
        }
        tvReset.setOnClickListener {
            SkinManager.loadSkin("")
        }
    }
}