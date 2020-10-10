package com.chencc.androidstudynotescode.customview.layoutManager

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chencc.androidstudynotescode.R

/**
 * @author Created by CHEN on 2020/9/6
 * @email 188669@163.com
 */
class CardRecyclerAdapter(var mList : MutableList<String>) : RecyclerView.Adapter<CardBaseViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardBaseViewHolder {
        return CardBaseViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_test, parent, false))
//        return CardBaseViewHolder(TextView(parent.context))
    }

    override fun getItemCount(): Int = mList.size

    override fun onBindViewHolder(holder: CardBaseViewHolder, position: Int) {
//        holder.view.text = mList[position]
//        holder.view.textSize = 20f
//        holder.view.setPadding(100,180, 100, 180)
//        holder.view.setBackgroundColor(Color.GRAY)

                holder.text.text = mList[position]
    }
}


class CardBaseViewHolder(var view : View) : RecyclerView.ViewHolder(view){
    val text = view.findViewById<TextView>(R.id.text)
}