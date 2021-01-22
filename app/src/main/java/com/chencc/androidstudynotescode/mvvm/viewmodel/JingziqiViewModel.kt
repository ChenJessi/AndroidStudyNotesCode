package com.chencc.androidstudynotescode.mvvm.viewmodel

import android.util.Log
import androidx.databinding.ObservableArrayMap
import androidx.databinding.ObservableField
import com.chencc.androidstudynotescode.mvvm.model.Board


class JingziqiViewModel {
    private var model = Board()
     val cells = ObservableArrayMap<String, String>()
     val winner = ObservableField<String?>()

    fun onResetSelected(){
        model.restart()
        winner.set(null)
        cells.clear()
    }

    fun onClickedCellAt(row : Int, col : Int){
        val playerThatMoved = model.mark(row, col)
        Log.i("TAG", "onClickedCellAt:   row : $row  col : $col    $playerThatMoved")
        if (playerThatMoved != null){
            cells["$row$col"] = playerThatMoved.toString()
            winner.set(model.getWinner()?.toString())
        }
    }
}