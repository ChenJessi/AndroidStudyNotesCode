package com.chencc.androidstudynotescode.kotlin

import androidx.core.util.rangeTo

fun main() {

    for (i in 0.. 9){
        println("i : $i")
    }

    for (i in 0 until 9){
        println("i1  : $i")
    }

    for (i in 9 downTo 0){
        println("i1  : $i")
    }

    if (5 in 0.. 9 ){
        println("i2  : 5包含在0..9里")
    }

    for (i in 0..9 step 3){
        println("i3  : $i")
    }
}