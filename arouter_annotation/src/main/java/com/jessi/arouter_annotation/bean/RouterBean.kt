package com.jessi.arouter_annotation.bean


import javax.lang.model.element.Element



/**
 * 最终路由要传递的对象
 *
 * 路由路径 path 的最终实体封装类
 * 例如：app 分组中的 MainActivity 对象， 对象中的更多属性
 */
data class RouterBean(
    var typeEnum: TypeEnum, // 枚举类型 ： Activity
    var element: Element,   // 类节点，JavaPoet/kotlinPoet 学习的时候，可以拿到很多信息
    var clazz: Class<*>, // 被注解的class 对象，例如： MainActivity.class
    var path : String,  // 路由地址 如 ：/app/MainActivity
    var group : String  // 路由组  如 ： app user
)

enum class TypeEnum {
    ACTIVITY
}