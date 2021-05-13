package com.jessi.arouter_api_java;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.util.LruCache;

import com.jessi.arouter_annotation_java.bean.RouterBean;

/**
 * @author Created by CHEN on 2021/5/13
 * @email 188669@163.com
 *
 * 第一步：查找 ARouter$$Group$$personal ---> ARouter$$Path$$personal
 */
public class RouterManager {
    private static final String TAG = "RouterManager";
    private String group; // 路由的组名 app，order，personal ...
    private String path;  // 路由的路径  例如：/order/Order_MainActivity


    // 提供性能  LRU缓存
    private LruCache<String, ARouterGroup> groupLruCache;
    private LruCache<String, ARouterPath> pathLruCache;

    // 为了拼接，例如:ARouter$$Group$$personal
    private final static String FILE_GROUP_NAME = "ARouter$$Group$$";

    private static RouterManager instance;

    public static RouterManager getInstance(){
        if (instance == null){
            synchronized (RouterManager.class){
                if (instance == null){
                    instance = new RouterManager();
                }
            }
        }
        return instance;
    }


    private RouterManager(){
        groupLruCache = new LruCache<>(100);
        pathLruCache = new LruCache<>(100);
    }

    public RouterManager build(String path){
        if (TextUtils.isEmpty(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("path 格式不正确，正确写法：如 /app/MainActivity");
        }

        if (path.lastIndexOf("/") == 0) { // 只写了一个 /
            throw new IllegalArgumentException("path 格式不正确，正确写法：如 /order/Order_MainActivity");
        }
        // 截取组名
        String finalGroup = path.substring(1,path.indexOf("/", 1));
        this.path = path;
        this.group = finalGroup;

        return this;
    }

    // 导航的方法
    public void navigation(Context context){
        // 测试demo 没有使用多个 model ，实际上要遵守 model == group
//        String groupClassName = context.getPackageName() + "." + FILE_GROUP_NAME + group;
        String groupClassName = context.getPackageName() + "." + FILE_GROUP_NAME + "app";

        try {
            // 从缓存里获取 ARouerGroup
            ARouterGroup loadGroup = groupLruCache.get(group);
            if (loadGroup == null){
                // 缓存里获取不到 再使用类加载的方式获取
                Class<?> aClazz = Class.forName(groupClassName);
                loadGroup = (ARouterGroup)aClazz.newInstance();
                groupLruCache.put(group, loadGroup);
            }
            if (loadGroup.getGroupMap().isEmpty()){
                throw new RuntimeException("路由表加载失败...");
            }
            // 读取 path 文件
            ARouterPath loadPath = pathLruCache.get(path);
            if (null == loadPath){
                Class<? extends ARouterPath> aClazz = loadGroup.getGroupMap().get(group);
                loadPath = aClazz.newInstance();

                pathLruCache.put(path, loadPath);
            }
            // 跳转
            if (null != path){
                if (loadPath.getPathMap().isEmpty()){
                    throw new RuntimeException("路由表 Path 加载失败...");
                }

                RouterBean routerBean = loadPath.getPathMap().get(path);
                if (null != routerBean){
                    switch (routerBean.getTypeEnum()){
                        case ACTIVITY:
                            Intent intent = new Intent(context, routerBean.getMyClass());
                            context.startActivity(intent);
                            break;
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }




}
