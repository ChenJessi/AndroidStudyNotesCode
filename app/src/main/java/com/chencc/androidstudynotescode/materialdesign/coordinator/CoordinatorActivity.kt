package com.chencc.androidstudynotescode.materialdesign.coordinator

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_coordinator.*

/**
 * CoordinatorLayout
 */
class CoordinatorActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        button1.setOnClickListener {
            startActivity(Intent(this@CoordinatorActivity, CoordinatorLayoutActivity::class.java))
        }
    }
}