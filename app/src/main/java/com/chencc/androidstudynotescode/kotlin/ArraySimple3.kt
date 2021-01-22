package com.chencc.androidstudynotescode.kotlin

import com.chencc.androidstudynotescode.mvvm.model.Cell


/**
 * array
 */
fun main() {
    val array : Array<Int> = Array<Int>(10) { value -> (value + 1)}

    for (i in array){
        println(i)
    }
    val a = arrayOf("", null,null)
    a[1] = "aaaa"
    println(" test1 :  "+a.toList())

    var b = arrayOf("", null,null)
    b[1] = "aaaa"
    println(" test2 :  "+b.toList())

    val c = arrayOfNulls<String>(3)
    c[1] = "aaaa"
    println(" test3 :  "+c.toList())

    val d: Array<Array<String?>> = Array(3) { arrayOfNulls<String>(3) }
    d[1][1] = "aaaa"
    println(" test3 :  "+d[1].toList())
}



