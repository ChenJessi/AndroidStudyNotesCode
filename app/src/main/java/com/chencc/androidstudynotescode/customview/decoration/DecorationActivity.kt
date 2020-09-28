package com.chencc.androidstudynotescode.customview.decoration

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.chencc.androidstudynotescode.R
import com.chencc.androidstudynotescode.adapter.RecyclerAdapter
import kotlinx.android.synthetic.main.activity_decoration.*

/**
 * Decoration 测试
 */
class DecorationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_decoration)
        recyclerView.layoutManager = LinearLayoutManager(this@DecorationActivity)
        recyclerView.adapter = DecorationAdapter(getData())
        recyclerView.addItemDecoration(TitleItemDecoration())
    }

    private fun getData(): MutableList<TitleBean> {
        val data = mutableListOf<TitleBean>()
        for ( index in 0..50){
            val title = when(index){
                in 0..15 -> "title"
                in 15..30 -> "item"
                else -> "child"
            }
            data.add(TitleBean(title," ChildView item $index"))
        }
        return data
    }
}