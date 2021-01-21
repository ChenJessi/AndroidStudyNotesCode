package com.chencc.androidstudynotescode.mvvm.viewmodel

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
        if (playerThatMoved != null){
            cells.put((row + col).toString(), playerThatMoved.toString())
            winner.set(model.getWinner()?.toString())
        }
    }
}