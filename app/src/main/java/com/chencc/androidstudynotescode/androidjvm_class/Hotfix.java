package com.chencc.androidstudynotescode.androidjvm_class;

import android.app.Application;
import android.os.Build;
import android.util.Log;

import java.io.File;
import java.lang.reflect.Array;
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
        private static void install(ClassLoader classLoader, List<File> additionalClassPathEntries, File optimizedDirectory){
            // 找到 pathList

        }
    }



}
