package com.jessi.arouter_api_java;

import android.app.Activity;
import android.util.Log;
import android.util.LruCache;

/**
 * @author Created by CHEN on 2021/5/19
 * @email 188669@163.com
 */
public class ParameterManager {
    private static ParameterManager instance;

    // key : 类名   value ：参数加载接口
    private LruCache<String, ParameterGet> cache;

    // 类名拼接
    private  String FILE_SUFFIX_NAME = "$$Parameter";

    public static ParameterManager getInstance(){
        if (instance == null){
            synchronized (ParameterManager.class){
                if (instance == null){
                    instance = new ParameterManager();
                }
            }
        }
        return instance;
    }

    private ParameterManager(){
        cache = new LruCache<>(100);
    }

    public void loadParameter(Activity activity){
        // 要寻找的类名
        String className = activity.getClass().getName() + FILE_SUFFIX_NAME;

        ParameterGet parameterGet = cache.get(className);
        if (parameterGet == null){
            // 缓存没有就使用类加载的方式
            try {
                Class<?> clazz = Class.forName(className);
                parameterGet = (ParameterGet) clazz.newInstance();
                cache.put(className, parameterGet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        parameterGet.getParameter(activity);
    }
}
