package com.chencc.androidstudynotescode.materialdesign

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.materialdesign.bottomappbar.BottomAppBarActivity
import com.chencc.androidstudynotescode.materialdesign.bottomsheets.BottomSheetsActivity
import com.chencc.androidstudynotescode.materialdesign.cardview.CardViewActivity
import com.chencc.androidstudynotescode.materialdesign.chips.ChipsActivity
import com.chencc.androidstudynotescode.materialdesign.coordinator.CoordinatorActivity
import com.chencc.androidstudynotescode.materialdesign.drawer.DrawerLayoutActivity
import com.chencc.androidstudynotescode.materialdesign.floatingactionbutton.FloatActionButtonActivity
import com.chencc.androidstudynotescode.materialdesign.materialbutton.MaterialButtonActivity
import com.chencc.androidstudynotescode.materialdesign.materialtext.MaterialTextActivity
import com.chencc.androidstudynotescode.materialdesign.navigation.NavigationActivity
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
        /**
         * This behavior is assigned to our dummy, MATCH_PARENT view inside this bottom sheet layout.
         * Through this behavior the dummy view can listen to touch/scroll events.
         * Our goal is to propagate them to the parent stream.
         *
         * It has to be done manually because by default CoordinatorLayouts don't propagate scroll events
         * to their parent. This is bad for CoordinatorLayouts inside other CoordinatorLayouts, since
         * the coordination works relies heavily on scroll events.
         *DummyBehavior
         * @param <DummyView> make sure it's not a nested-scrolling-enabled view or this will break.
         */
    }
}