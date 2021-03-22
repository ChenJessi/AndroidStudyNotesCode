package com.jessi.arouter_api

/**
 * Group 分组
 *
 * 例如：
 *   key:   app
 *   value:  ARouterPath
 */
interface ARouterGroup {
    /**
     * @return  key:"user"      value:系列的 user 组下面所有的（path---class）
     */
    fun getGroupMap() :  Map<String, Class<out ARouterPath>?>
}