package com.chencc.androidstudynotescode.kotlin

import kotlin.reflect.KClass

fun main() {

//
//    `   `("test")
//    ` testFun  `("testFun")
//    ` 9999  `("9999")
//    val i = 1
//    when{
//        i < 2 ->{
//            println("1111")
//       }
//        i < 3 -> {
//            println("2222")
//        }
//        else -> {
//            println("3333")
//        }
//
//    }
    var len = 1
    var b = 2
    println("test1 :  ${len.run { b}}")
    println("test2 :  ${b.also { len = it }}")



}
//
//fun `   `(str: String){
//    println("str:$str")
//}
//
//fun ` testFun  `(str: String){
//    println("str:$str")
//}
//
//fun ` 9999  `(str: String){
//    println("str:$str")
//}