package com.chencc.androidstudynotescode.materialdesign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.materialdesign.bottomappbar.BottomAppBarActivity
import com.chencc.androidstudynotescode.materialdesign.bottomsheets.BottomSheetsActivity
import com.chencc.androidstudynotescode.materialdesign.cardview.CardViewActivity
import com.chencc.androidstudynotescode.materialdesign.chips.ChipsActivity
import com.chencc.androidstudynotescode.materialdesign.constraintlayout.ConstraintLayoutActivity
import com.chencc.androidstudynotescode.materialdesign.coordinator.CoordinatorActivity
import com.chencc.androidstudynotescode.materialdesign.drawer.DrawerLayoutActivity
import com.chencc.androidstudynotescode.materialdesign.floatingactionbutton.FloatActionButtonActivity
import com.chencc.androidstudynotescode.materialdesign.materialbutton.MaterialButtonActivity
import com.chencc.androidstudynotescode.materialdesign.materialtext.MaterialTextActivity
import com.chencc.androidstudynotescode.materialdesign.nestedscroll.nestedscrolling.NestedScrolling1Activity
import com.chencc.androidstudynotescode.materialdesign.nestedscroll.tradition.NestedTraditionActivity
import com.chencc.androidstudynotescode.materialdesign.navigation.NavigationActivity
import com.chencc.androidstudynotescode.materialdesign.nestedscroll.NestScrollActivity
import com.chencc.androidstudynotescode.materialdesign.snackbar.SnackbarActivity
import com.chencc.androidstudynotescode.materialdesign.tab.TabActivity
import com.chencc.androidstudynotescode.materialdesign.toolbar.ToolbarActivity
import com.chencc.androidstudynotescode.materialdesign.viewpager2.ViewPager2Activity
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
        button2.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, ToolbarActivity::class.java))
        }
        button3.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, ViewPager2Activity::class.java))
        }
        button4.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, CardViewActivity::class.java))
        }
        button5.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, BottomAppBarActivity::class.java))
        }
        button6.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, BottomSheetsActivity::class.java))
        }
        button7.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, ChipsActivity::class.java))
        }
        button8.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, FloatActionButtonActivity::class.java))
        }
        button9.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, DrawerLayoutActivity::class.java))
        }
        button10.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, MaterialButtonActivity::class.java))
        }
        button11.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, MaterialTextActivity::class.java))
        }
        button12.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, NavigationActivity::class.java))
        }
        button13.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, SnackbarActivity::class.java))
        }
        button14.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, TabActivity::class.java))
        }
        button15.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, NestScrollActivity::class.java))
        }
        button16.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, NestedTraditionActivity::class.java))
        }
        button17.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, NestedScrolling1Activity::class.java))
        }

        button18.setOnClickListener {
            startActivity(Intent(this@MaterialDesignActivity, ConstraintLayoutActivity::class.java))
        }
    }
}