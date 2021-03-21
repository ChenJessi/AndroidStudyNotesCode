package com.jessi.arouter_annotation

@Target(AnnotationTarget.TYPE)// 该注解作用在类上
@Retention(AnnotationRetention.BINARY)  //要在编译器进行一些预处理操作，注解会在class文件中存在 但jvm会忽略
annotation class ARouter {
    // 详细路由路径 必填 如："/app/MainActivity"
    fun path() : String
    // 路由组名，如果开发者不填写，可以由path中截取出来
    fun group() : String = ""
}