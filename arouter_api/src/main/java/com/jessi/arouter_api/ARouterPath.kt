package com.jessi.arouter_api

import com.jessi.arouter_annotation.bean.RouterBean


/**
 * 其实就是路由组 group 对应的 --- 详细 Path 加载数据接口 ARouterPath
 *
 * 例如：
 *    key:   /app/MainActivity1
 *    value:  RouterBean(MainActivity1.class)
 */
interface  ARouterPath {
    /**
     * @return 例如 ： key ： "/order/MainActivity"
     */
    fun getPathMap() : Map<String, RouterBean>
}