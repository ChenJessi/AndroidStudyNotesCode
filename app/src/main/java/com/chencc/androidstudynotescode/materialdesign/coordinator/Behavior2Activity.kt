package com.chencc.androidstudynotescode.materialdesign.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_behavior2.*


/**
 * behavior
 * 嵌套滑动
 */
class Behavior2Activity : AppCompatActivity(){

    val mList by lazy { mutableListOf<String>().apply {
        repeat(20) { i ->
            add("  item  $i")}
    } }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_behavior2)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RecyclerAdapter(mList)
    }

}