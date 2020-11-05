package com.chencc.androidstudynotescode.materialdesign.navigation

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_navigation.*

/**
 * Navigation
 */
class NavigationActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)
        llMsg.setOnClickListener {
            drawerLayout.closeDrawers()
        }
        navView.setNavigationItemSelectedListener {
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }
    }
}