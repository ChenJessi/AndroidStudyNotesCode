package com.chencc.androidstudynotescode.mvvm.model

import android.os.Build.VERSION_CODES.O
import android.util.Log


class Board {

    private val cells: Array<Array<Cell>> = Array(3) { arrayOf(Cell(), Cell(), Cell()) }

    private var winner : Player? = null

    private var currentTurn : Player = Player.X

    private var state : GameState = GameState.IN_PROGRESS
    init {
        restart()
    }

    /**
     * 开始一个新游戏，重置状态
     */
    fun restart(){
        clearCells()
        winner = null
        currentTurn = Player.X
        state = GameState.IN_PROGRESS
    }


    fun mark(row : Int, col : Int) : Player? {
        var playerThatMoved : Player? = null

        if (isValid(row, col)){
            cells[row][col]?.value = currentTurn
            playerThatMoved = currentTurn
            cells[row][col]?.value = currentTurn
            if (isWinningMoveByPlayer(currentTurn, row, col)){
                state = GameState.FINISHED
                winner = currentTurn
            }else{
                //切换棋手
                flipCurrentTurn()
            }
        }
        return playerThatMoved
    }

    fun getWinner() : Player? = winner


    private fun clearCells(){
        cells.forEach {
            it.forEach { cell ->
                cell?.value = null
            }
        }
    }


    private fun isValid(row : Int, col : Int) : Boolean {
        return when {
            state == GameState.FINISHED -> {
                false
            }
            isCellValueAlreadySet(row, col) -> {
                false
            }
            else -> {
                true
            }
        }
    }

    private fun isCellValueAlreadySet(row : Int, col: Int) : Boolean {
        return cells[row][col]?.value != null
    }


    /**
     * @param player
     * @param currentRow
     * @param currentCol
     * @return 如果当前行、当前列、或者两天对角线为同一位棋手下的棋子返回为真
     */
    private fun isWinningMoveByPlayer(
        player: Player,
        currentRow: Int,
        currentCol: Int
    ): Boolean {
        return (cells[currentRow][0]?.value === player // 3-行
                && cells[currentRow][1]?.value === player
                && cells[currentRow][2]?.value === player
                )
                || (cells[0][currentCol]?.value === player // 3-列
                && cells[1][currentCol]?.value === player
                && cells[2][currentCol]?.value === player
                )
                || (currentRow == currentCol // 3-对角线
                && cells[0][0]?.value === player
                && cells[1][1]?.value === player
                && cells[2][2]?.value === player
                )
                || (currentRow + currentCol == 2 // 3-反对角线
                && cells[0][2]?.value === player
                && cells[1][1]?.value === player
                && cells[2][0]?.value === player)
    }

    fun flipCurrentTurn(){
        currentTurn = if (currentTurn === Player.X) Player.O else Player.X
    }
}