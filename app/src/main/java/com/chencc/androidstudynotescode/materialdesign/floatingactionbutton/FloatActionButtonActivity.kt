package com.chencc.androidstudynotescode.materialdesign.floatingactionbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_floataction_button.*

/**
 * @author Created by CHEN on 2020/11/3
 * @email 188669@163.com
 *
 * FloatActionButton
 */
class FloatActionButtonActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_floataction_button)

        button1.setOnClickListener {
            if (button1.isExtended){
                button1.shrink()
            }else{
                button1.extend()
            }
        }
    }
}