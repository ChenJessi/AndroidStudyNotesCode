package com.chencc.androidstudynotescode.kotlin

import java.io.File

fun main() {


//    var a = 3.rangeTo(9)
//    for (i in a){
//        println(i)
//    }

//    val stu =  test1(4545, "name", 'M')
//    val stu1 =  test1(4545, "name", 'M')

//    val(n1, n2, n3 ,n4) = stu
//
//    println(n1)
//    println(n2)
//    println(n3)
//    println(n4)

//    println((stu + stu1).id)        // 输出 9090
//    println((stu + stu1).name)      // 输出 name


//    var test2 = test2(1, "test")
//    val(v1 ,v2) = test2.copy(id = 2)
//    val t2 = test2.copy(id = 2)
//    println(v1)
//    println(v2)

    var a = arrayOf<String>("0","1","2","3")
    println("${a.toList().toString()}")
    a[0] = "aaa"
    println("${a.toList()}")

}

/**
 * 重载 plus函数
 */
operator fun test1.plus(test1: test1) : test1{
    this.id += test1.id
    return this
}

 class test1(var id: Int, var name: String ,var sex: Char){

//     // component 不能写错
     operator fun component1(): Int = id

     operator fun component2(): String = name

     operator fun component3(): Char = sex

     operator fun component4(): String = "KT Study OK"
}


/**
 * copy
 */
data class test2(var id : Int = 0,  var name : String = "")