package com.chencc.androidstudynotescode.materialdesign.constraintlayout

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_constraint_layout.*

/**
 * ConstraintLayout
 */
class ConstraintLayoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_constraint_layout)
        button1.setOnClickListener {
            startActivity(Intent(this@ConstraintLayoutActivity, CircularRevealHelperActivity::class.java))
        }
        button2.setOnClickListener {
            startActivity(Intent(this@ConstraintLayoutActivity, FlowConstraintActivity::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(this@ConstraintLayoutActivity, LayerConstraintActivity::class.java))
        }

    }
}