package com.chencc.androidstudynotescode.draw_text

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.skin.SkinTestActivity
import kotlinx.android.synthetic.main.activity_drawtext.*

/**
 * ���ֻ���
 */
class DrawTextActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drawtext)

        /**
         * ���ָ�����
         */
        button1.setOnClickListener {
            startActivity(Intent(this@DrawTextActivity, AssistLineActivity::class.java))
        }
        /**
         * ���ֲ�����ʾ
         */
        button2.setOnClickListener {
            startActivity(Intent(this@DrawTextActivity, TextMeasureActivity::class.java))
        }
        /**
         * ��ɫ������ʾ
         */
        button3.setOnClickListener {
            startActivity(Intent(this@DrawTextActivity, ColorChangeActivity::class.java))
        }
    }
}