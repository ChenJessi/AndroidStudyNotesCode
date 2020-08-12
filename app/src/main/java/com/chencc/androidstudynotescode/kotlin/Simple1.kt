package com.chencc.androidstudynotescode.kotlin

fun main() {





//    println("infix test  ${"test" append "  myadd"}")
//    println("infix test add1  ${1 append 2}")
//    println("infix test add1  ${1 add 2}")


//    "name".mytest{"test name"}


    val a : String = "name"
    val b : String? = "name"
    println(a === b) // true

//    var c = null
//    var d = ""
//    println(a == b) // true


    val test1:Int =  10000
    val test2:Int? =  10000
    // ---  比较对象地址
    println(test1 === test2) // false
    // --- 比较值
    println(test1 == test2) // true
}

/**
 *  infix
 */
infix fun <T,R> T.append(r : R) = this.toString() + r.toString()

infix fun Int.add(int: Int) = this + int


fun <T> T.mytest(block : ()-> T) : T{
    println("mytest $this")
    println("mytest ${block()}")
    return this
}
