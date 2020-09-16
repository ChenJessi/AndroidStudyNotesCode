package com.chencc.androidstudynotescode.draw_text

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R

/**
 * 文字辅助线
 */
class AssistLineActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_assist_line)
        setContentView(TextMeasureView(this))
    }
}


/**
 * 文字测量演示
 */
class TextMeasureActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(TextMeasureViewTest(this))
    }
}