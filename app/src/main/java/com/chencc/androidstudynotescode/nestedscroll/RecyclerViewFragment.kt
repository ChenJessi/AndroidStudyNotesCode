package com.chencc.androidstudynotescode.nestedscroll

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.R
import kotlinx.android.synthetic.main.fragment_recycler_view.*

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class RecyclerViewFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recycler_view, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = RecyclerAdapter(getData())
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            val THRESHOLD_LOAD_MORE = 3
            var hasLoadMore = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    hasLoadMore = false
                }
                if (newState != RecyclerView.SCROLL_STATE_DRAGGING && !hasLoadMore){
                    var lastPosition = ((recyclerView.layoutManager) as LinearLayoutManager).findLastVisibleItemPosition()
                    var offset = recyclerView.adapter?.let { it.itemCount - lastPosition - 1 } ?:0
                    if (offset <= THRESHOLD_LOAD_MORE){
                        hasLoadMore = true;
                        adapter.mList.addAll(getData());
                        adapter.notifyDataSetChanged();
                    }
                }

                super.onScrollStateChanged(recyclerView, newState)
            }
        })
    }


    private fun getData(): MutableList<String> {
        val data = mutableListOf<String>()
        for ( index in 0..19){
            data.add("ChildView item $index")
        }
        return data
    }
}