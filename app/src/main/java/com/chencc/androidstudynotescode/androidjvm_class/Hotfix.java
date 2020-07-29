package com.chencc.androidstudynotescode.androidjvm_class;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * android 类加载实例demo
 */
public class Hotfix {

    private static final String TAG = "Hotfix";

    public static void installPatch(Application application, File file){
        // 1.  获得 ClassLoader， PathClassLoader

        ClassLoader classLoader = application.getClassLoader();

        List<File> files = new ArrayList<>();
        if (file.exists()){
            files.add(file);
        }
        File dexOptDir = application.getCacheDir();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            try {
                NewClassLoaderInjector.inject(application, classLoader, files);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
                // sdk 23  6.0及以上
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

                }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
                    // sdk 19  4.4及以上

                }else {  // >= 14

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static final class V23{
        private static void install(ClassLoader classLoader, List<File> additionalClassPathEntries, File optimizedDirectory) throws NoSuchFieldException, IllegalAccessException {
            // 找到 pathList
            Field pathListField = ShareReflectUtil.findField(classLoader, "pathList");
            Object dexPathList = pathListField.get(classLoader);

            ArrayList<IOException> suppressedExceptions = new ArrayList<>();
            // 从pathList 中找到 makePathElements 方法执行
            // / 得到补丁创建的 Element[]

        }
    }



    private static Object[] makePathElements( Object dexPathList, ArrayList<File> files, File optimizedDirectory, ArrayList<IOException> suppressedExceptions) throws NoSuchFieldException {

        // 通过阅读 android6、7、8、9源码，都存在makePathElements方法
        Method makePathElements = ShareReflectUtil.findField(dexPathList, "makePathElements");

    }

}
