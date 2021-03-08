package com.chencc.androidstudynotescode.utils.proto

import com.chencc.androidstudynotescode.Helloword


fun main() {

    val helloRequest = Helloword.HelloRequest.newBuilder()
        .setName("Jessi")
        .setAge(18)
        .build()
    // 获得序列化后数据的大小
    val serializedSize = helloRequest.serializedSize

    println("serializedSize : $serializedSize")

    println("{\"name\" : \"Jessi\",\"age\": 18}".toByteArray().size)
}