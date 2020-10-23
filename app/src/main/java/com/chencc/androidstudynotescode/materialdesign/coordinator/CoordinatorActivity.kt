package com.chencc.androidstudynotescode.materialdesign.coordinator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_coordinator.*


/**
 * CoordinatorLayout 练习
 *
 */
class CoordinatorActivity : AppCompatActivity(){
    val mList by lazy { mutableListOf<String>().apply {
        repeat(20){i ->
            add("ChildView item $i")
        }
    }}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coordinator)

        recyclerView.layoutManager = LinearLayoutManager(this@CoordinatorActivity)
        recyclerView.adapter = RecyclerAdapter(mList)


    }
}