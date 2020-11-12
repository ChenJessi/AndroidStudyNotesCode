package com.chencc.androidstudynotescode.materialdesign.coordinator

import android.graphics.Color
import android.os.Bundle
import android.view.WindowManager
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.RecyclerAdapter
import com.qmuiteam.qmui.util.QMUIStatusBarHelper
import kotlinx.android.synthetic.main.activity_coordinator_layout.*


/**
 * CoordinatorLayout 练习
 *
 */
class CoordinatorLayoutActivity : AppCompatActivity(){
    val mList by lazy { mutableListOf<String>().apply {
        repeat(20){i ->
            add("ChildView item $i")
        }
    }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //全屏模式  状态栏会被覆盖
        //window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 沉浸式状态栏 将状态栏设置为半透明状态
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
        setContentView(R.layout.activity_coordinator_layout)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

         toolbar.setNavigationOnClickListener {
             onBackPressed()
         }

        val layoutParams = toolbar.layoutParams as FrameLayout.LayoutParams
        layoutParams.topMargin = QMUIStatusBarHelper.getStatusbarHeight(this@CoordinatorLayoutActivity)

        recyclerView.layoutManager = LinearLayoutManager(this@CoordinatorLayoutActivity)
        recyclerView.adapter = RecyclerAdapter(mList)

        collapsingToolbarLayout.title = "嗯嗯嗯"
        // 展开的 title颜色
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT)
        // 折叠的 title颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE)
//        appBarLayout.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
////            if (abs(verticalOffset) >= appBarLayout!!.totalScrollRange * 0.6 ){
////                collapsingToolbarLayout.title = "嗯嗯嗯"
////            }else{
////                collapsingToolbarLayout.title = ""
////            }
//        })

    }
}