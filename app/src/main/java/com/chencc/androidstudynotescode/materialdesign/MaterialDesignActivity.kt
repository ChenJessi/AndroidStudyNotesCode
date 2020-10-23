package com.chencc.androidstudynotescode.materialdesign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.materialdesign.coordinator.CoordinatorActivity
import kotlinx.android.synthetic.main.activity_material_design.*


/**
 * MaterialDesign 控件练习
 */
class MaterialDesignActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_material_design)

        button1.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, CoordinatorActivity::class.java))
        }
    }
}