package com.chencc.androidstudynotescode.kotlin


/**
 * array
 */
fun main() {
    val array : Array<Int> = Array<Int>(10) { value -> (value + 1)}

    for (i in array){
        println(i)
    }


}



