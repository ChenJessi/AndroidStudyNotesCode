package com.chencc.androidstudynotescode.customview.decoration

import android.graphics.Color
import android.util.Log
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class DecorationAdapter(var mList : MutableList<TitleBean>) : RecyclerView.Adapter<BaseViewHolder>(){

    fun isHeadTitle(position: Int) : Boolean{
        return when{
            position == 0 -> true
            mList[position].title != mList[position-1].title -> true
            else -> false
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(
            TextView(
                parent.context
            )
        )
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        holder.view.text = mList[position].name
        holder.view.textSize = 20f
        holder.view.setBackgroundColor(Color.WHITE)
        holder.view.setPadding(0,30, 0, 30)

    }
}


class BaseViewHolder(var view : TextView) : RecyclerView.ViewHolder(view){

}