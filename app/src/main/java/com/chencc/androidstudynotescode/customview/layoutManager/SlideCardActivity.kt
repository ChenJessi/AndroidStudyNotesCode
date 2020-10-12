package com.chencc.androidstudynotescode.customview.layoutManager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.activity_slide_card.*

/**
 * 自定义 LayoutManager
 * 滑动的卡片布局  RecyclerView
 */
class SlideCardActivity : AppCompatActivity(){
    val mList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_slide_card)

        for (i in 0..5){
            mList.add("item $i")
        }
//        recyclerView.layoutManager = LinearLayoutManager(this@SlideCardActivity)
        val adapter = CardRecyclerAdapter(mList)
        recyclerView.layoutManager = SlideCardLayoutManager()
        recyclerView.adapter = adapter
        val slideCallback = SlideCallback(adapter, mList)
        val itemTouchHelper = ItemTouchHelper(slideCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

//        recyclerView.layoutManager = SlideCardLayoutManagerD()
//        recyclerView.adapter = adapter
//        val slideCallback = SlideCallbackD(recyclerView, adapter, mList)
//        val itemTouchHelper = ItemTouchHelper(slideCallback)
//        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}