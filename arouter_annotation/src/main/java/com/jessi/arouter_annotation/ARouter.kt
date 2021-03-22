package com.jessi.arouter_annotation

/**
 * <strong>Activity使用的布局文件注解</strong>
 *
 * @Target
 *  AnnotationTarget.CLASS	类、接口、对象声明和注解类声明
 *  AnnotationTarget.ANNOTATION_CLASS	其他注解类型声明
 *  AnnotationTarget.TYPE_PARAMETER	通用类型参数（还不支持）。
 *  AnnotationTarget.PROPERTY	属性声明
 *  AnnotationTarget.FIELD	字段声明，包括属性的支持字段
 *  AnnotationTarget.LOCAL_VARIABLE	局部变量声明
 *  AnnotationTarget.VALUE_PARAMETER	用于函数或构造函数参数值声明
 *  AnnotationTarget.CONSTRUCTOR	用于构造函数声明
 *  AnnotationTarget.FUNCTION	用于函数声明，不包括构造函数
 *  AnnotationTarget.PROPERTY_GETTER	只用于属性的getter访问器声明
 *  AnnotationTarget.PROPERTY_SETTER	只用于属性的setter访问器声明
 *  AnnotationTarget.TYPE	类型使用
 *  AnnotationTarget.EXPRESSION	任何表达式
 *  AnnotationTarget.FILE	文件
 *  AnnotationTarget.TYPEALIAS	类型别名
 *
 *@Retention(AnnotationRetention.RUNTIME)  // 注解会在class字节码文件中存在，jvm加载时可以通过反射获取到该注解的内容
 *
 *  生命周期：SOURCE < BINARY < RUNTIME
 *  1、一般如果需要在运行时去动态获取注解信息，用RUNTIME注解
 *  2、要在编译时进行一些预处理操作,   用 BINARY 注解, 注解会在class文件中存在，但是在运行时会被丢弃
 *  3. 做一些检查性的操作，如@Override，用 SOURCE 源码注解， 注解仅在源码级别，在编译的时候丢弃该注解
 */


@Target(AnnotationTarget.TYPE)// 该注解作用在类上
@Retention(AnnotationRetention.BINARY)  //要在编译器进行一些预处理操作，注解会在class文件中存在 但jvm会忽略
annotation class ARouter(
    // 详细路由路径 必填 如："/app/MainActivity"
    val path : String,
    // 路由组名，如果开发者不填写，可以由path中截取出来
    val group: String = ""
)



