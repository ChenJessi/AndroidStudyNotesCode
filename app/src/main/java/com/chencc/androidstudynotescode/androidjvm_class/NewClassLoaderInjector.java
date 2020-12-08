package com.chencc.androidstudynotescode.androidjvm_class;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;

import androidx.annotation.Nullable;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import dalvik.system.DexFile;
import dalvik.system.PathClassLoader;

/**
 *
 */
public class NewClassLoaderInjector {

    public static ClassLoader inject(Application application, ClassLoader oldClassLoader, List<File> files) throws NoSuchFieldException, IllegalAccessException {
        // 分发加载任务的加载器，作为我们自己加载器的父加载器
        DispatchClassLoader dispatchClassLoader = new DispatchClassLoader(oldClassLoader, application.getClass().getName());

        // 创建自己的加载器
        ClassLoader newClassLoader = createNewClassLoader(application, oldClassLoader, dispatchClassLoader, files);
        dispatchClassLoader.setNewClassLoader(newClassLoader);
        // 将自己的 加载器  newClassLoader 注入 application
        doInject(application, newClassLoader);
        return null;
    }




    private static ClassLoader createNewClassLoader(Context context, ClassLoader oldClassLoader, ClassLoader dispatchClassLoader, List<File> patchs) throws NoSuchFieldException, IllegalAccessException {

        // 得到 pathList
        Field pathListField = ShareReflectUtil.findField(oldClassLoader, "pathList");
        Object oldPathList = pathListField.get(oldClassLoader);

        // dexElements
        Field dexElementsField = ShareReflectUtil.findField(oldPathList, "dexElements");

        Object[] oldDexElemtnts = (Object[]) dexElementsField.get(oldPathList);

        // 从 elements 中得到 dexFile
        Field dexFileField = ShareReflectUtil.findField(oldDexElemtnts[0], "dexFile");

        // 获得原始的 dexPath 用于构造 calssLoader
        StringBuilder dexPathBuilder = new StringBuilder();
        String packageName = context.getPackageName();
        boolean isFirstItem = true;
        for (File patch : patchs) {
            if (isFirstItem){
                isFirstItem = false;
            }else {
                dexPathBuilder.append(File.pathSeparator);
            }
            dexPathBuilder.append(patch.getAbsolutePath());
        }

        for (Object oldDexElemtnt : oldDexElemtnts) {
            String dexPath = null;
            DexFile dexFile = (DexFile) dexFileField.get(oldDexElemtnt);
            if (dexFile != null){
                dexPath = dexFile.getName();
            }
            if (dexPath == null || dexPath.isEmpty()){
                continue;
            }
            if (!dexPath.contains("/" + packageName)){
                continue;
            }

            if (isFirstItem){
                isFirstItem = false;
            }else {
                dexPathBuilder.append(File.pathSeparator);
            }

            dexPathBuilder.append(dexPath);
        }

        final String combinedDexPath = dexPathBuilder.toString();

        // apk中的native 库 （so） 文件目录，用于构造 classloader

        Field nativeLibraryDirectoriesField = ShareReflectUtil.findField(oldPathList, "nativeLibraryDirectories");
        List<File> oldNativeLibraryDirectories = (List<File>) nativeLibraryDirectoriesField.get(oldPathList);

        StringBuilder libraryPathBuilder = new StringBuilder();
        isFirstItem = true;
        for (File libDir : oldNativeLibraryDirectories) {
            if (libDir == null){
                continue;
            }
            if (isFirstItem){
                isFirstItem = false;
            }else {
                libraryPathBuilder.append(File.pathSeparator);
            }
            libraryPathBuilder.append(libDir.getAbsolutePath());
        }

        String combinedLibraryPath = libraryPathBuilder.toString();

        // 创建自己id类加载器
        ClassLoader result = new PathClassLoader(combinedDexPath, combinedLibraryPath, dispatchClassLoader);

        ShareReflectUtil.findField(oldPathList, "definingContext").set(oldPathList, result);
        ShareReflectUtil.findField(result, "parent").set(result, dispatchClassLoader);

        return result;
    }



    private static void doInject(Application application, ClassLoader classLoader) throws NoSuchFieldException, IllegalAccessException {
        Thread.currentThread().setContextClassLoader(classLoader);

        Context baseContext = (Context) ShareReflectUtil.findField(application, "mBase").get(application);
        Object basePackageInfo = ShareReflectUtil.findField(baseContext, "mPackageInfo").get(baseContext);

        ShareReflectUtil.findField(basePackageInfo, "mClassLoader").set(basePackageInfo, classLoader);

        if (Build.VERSION.SDK_INT < 27){
            Resources res = application.getResources();

            try {
                ShareReflectUtil.findField(res, "mClassLoader").set(res, classLoader);

                final Object drawableInflater = ShareReflectUtil.findField(res, "mDrawableInflater").get(res);
                if (drawableInflater != null){
                    ShareReflectUtil.findField(res, "mClassLoader").set(res, classLoader);
                }
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }
    }



    private static final class DispatchClassLoader extends ClassLoader{

        private final String mApplicationClassName;
        private final ClassLoader oldClassLoader;

        private ClassLoader newClassLoader;

        private final ThreadLocal<Boolean> mCallFindClassOfLeafDirectly = new ThreadLocal<Boolean>(){
            @Nullable
            @Override
            protected Boolean initialValue() {
                return false;
            }
        };

        public DispatchClassLoader(ClassLoader oldClassLoader, String mApplicationClassName) {
            super(ClassLoader.getSystemClassLoader());
            this.oldClassLoader = oldClassLoader;
            this.mApplicationClassName = mApplicationClassName;
        }

        public void setNewClassLoader(ClassLoader newClassLoader) {
            this.newClassLoader = newClassLoader;
        }


        @Override
        protected Class<?> findClass(String name) throws ClassNotFoundException {
            System.out.println("find : "+ name);
            if (mCallFindClassOfLeafDirectly.get()){
                return null;
            }

            // 1. application 不需要修复 使用原本的类加载器获得
            if (name.equals(mApplicationClassName)){
                return findClass(oldClassLoader, name);
            }

            // 2. 加载热修复框架的类， 不需要修复，使用原本的类加载器获得
            if (name.startsWith("com.chencc.androidstudynotescode.androidjvm_class.")){
                return findClass(oldClassLoader, name);
            }

            try {
                return findClass(newClassLoader, name);
            } catch (ClassNotFoundException e){
                return findClass(oldClassLoader, name);
            }

        }

        private Class<?> findClass(ClassLoader classLoader, String name) throws ClassNotFoundException {
            try {
                // 双亲委托  所以可能会stackoverflow死循环 防止这个情况
                mCallFindClassOfLeafDirectly.set(true);
                return classLoader.loadClass(name);
            } finally {
                mCallFindClassOfLeafDirectly.set(false);
            }

        }

    }

}
