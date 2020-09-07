package com.chencc.androidstudynotescode.nestedscroll

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class RecyclerAdapter(var mList : MutableList<String>) : RecyclerView.Adapter<BaseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.view.text = mList[position]
        holder.view.textSize = 20f
        holder.view.setPadding(0,30, 0, 30)
    }
}


class BaseViewHolder(var view : TextView) : RecyclerView.ViewHolder(view){

}