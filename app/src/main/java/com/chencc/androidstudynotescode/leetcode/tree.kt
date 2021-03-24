package com.chencc.androidstudynotescode.leetcode

import android.util.SparseIntArray


fun main() {

    // 斐波那契数列问题
    fib1()
}





/**
 * 斐波那契数列问题
 */

fun fib1(){
//   val int =  fib1(20)
//   val int =  fib2(20)
//   val int =  fib3(20)
   val int =  fib4(20)
    println("斐波那契数列：fib : $int")
}

/**
 * 暴力递归
 * 时间复杂度为 O(2^n)
 */
fun fib1(n : Int) : Int {
    if(n == 1 || n == 2) return 1
    return fib1(n - 1 ) + fib1(n - 2)
}

/**
 * 用一个数组记录，避免重复计算
 * 时间复杂度为 O(n)
 */
fun fib2(n: Int) : Int {
    if (n == 1) return 1
    val array = Array(n + 1){0}
    println("array : ${array.toList()}")
    val int = helperFib2(array, n)
    println("array : ${array.toList()}")
    return int
}

fun helperFib2(array : Array<Int>, n : Int) : Int {
    if(n == 1 || n == 2) return 1

    // 已经计算过就直接取值
    if (array[n] != 0) return array[n]
    // 否则计算然后存起来
    array[n] = helperFib2(array, n -1) + helperFib2(array, n -2)
    return array[n]
}


/**
 * 数组迭代法
 */
fun fib3(n : Int) : Int{
    if(n == 1 || n == 2) return 1
    val array = Array(n + 1){0}
    array[1] = 1
    array[2] = 1
    for (i in 3..n){
        array[i] = array[i - 1] + array[i - 2]
    }
    return array[n]
}

/**
 *  用 变量取代数组
 *  时间复杂度 O(1)
 */
fun fib4(int: Int) : Int{
    if(int == 1 || int == 2) return 1

    var m = 1
    var n = 1
    for (i in 3..int){
        val sum = m + n
        m = n
        n = sum
    }
    return n
}
