package com.chencc.androidstudynotescode.materialdesign.materialbutton

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_material_button.*

/**
 * @author Created by CHEN on 2020/11/4
 * @email 188669@163.com
 * MaterialButton
 */
class MaterialButtonActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_button)

        button.setOnClickListener {
            button1.isEnabled = !button1.isEnabled
        }
    }
}