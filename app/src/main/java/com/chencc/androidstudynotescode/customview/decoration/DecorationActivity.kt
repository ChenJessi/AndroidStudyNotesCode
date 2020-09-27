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
        for ( index in 0..19){
            val title = if (index < 10) "title" else "item"
            data.add(TitleBean(title," ChildView item $index"))
        }
        return data
    }
}