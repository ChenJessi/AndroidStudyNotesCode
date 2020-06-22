package com.chencc.mylibrary.utils;

import android.os.Build;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class V19 {
    private static final String TAG = "V19";

    private static Field findField(Object instance, String name) throws NoSuchFieldException {
        Class clazz = instance.getClass();

        while (clazz != null) {
            try {
                Field e = clazz.getDeclaredField(name);
                if (!e.isAccessible()) {
                    e.setAccessible(true);
                }

                return e;
            } catch (NoSuchFieldException var4) {
                clazz = clazz.getSuperclass();
            }
        }

        throw new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    private static Method findMethod(Object instance, String name, Class... parameterTypes)
            throws NoSuchMethodException {
        Class clazz = instance.getClass();
//        Method[] declaredMethods = clazz.getDeclaredMethods();
//        System.out.println("  findMethod ");
//        for (Method m : declaredMethods) {
//            System.out.print(m.getName() + "  : ");
//            Class<?>[] parameterTypes1 = m.getParameterTypes();
//            for (Class clazz1 : parameterTypes1) {
//                System.out.print(clazz1.getName() + " ");
//            }
//            System.out.println("");
//        }
        while (clazz != null) {
            try {
                Method e = clazz.getDeclaredMethod(name, parameterTypes);
                if (!e.isAccessible()) {
                    e.setAccessible(true);
                }

                return e;
            } catch (NoSuchMethodException var5) {
                clazz = clazz.getSuperclass();
            }
        }
        throw new NoSuchMethodException("Method " + name + " with parameters " + Arrays.asList
                (parameterTypes) + " not found in " + instance.getClass());
    }


    private static void expandFieldArray(Object instance, String fieldName, Object[]
            extraElements) throws NoSuchFieldException, IllegalArgumentException,
            IllegalAccessException {
        Field jlrField = findField(instance, fieldName);
        Object[] original = (Object[]) ((Object[]) jlrField.get(instance));
        Object[] combined = (Object[]) ((Object[]) Array.newInstance(original.getClass()
                .getComponentType(), original.length + extraElements.length));
        System.arraycopy(original, 0, combined, 0, original.length);
        System.arraycopy(extraElements, 0, combined, original.length, extraElements.length);
        jlrField.set(instance, combined);
    }





    public static void install(ClassLoader loader, List<File> additionalClassPathEntries,
                                File optimizedDirectory) throws IllegalArgumentException,
            IllegalAccessException, NoSuchFieldException, InvocationTargetException,
            NoSuchMethodException {

        Field pathListField = findField(loader, "pathList");
        Object dexPathList = pathListField.get(loader);
        ArrayList suppressedExceptions = new ArrayList();
        Log.d(TAG, "Build.VERSION.SDK_INT " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 23) {
            expandFieldArray(dexPathList, "dexElements", makePathElements(dexPathList, new
                            ArrayList(additionalClassPathEntries), optimizedDirectory,
                    suppressedExceptions));
        } else {
            expandFieldArray(dexPathList, "dexElements", makeDexElements(dexPathList, new
                            ArrayList(additionalClassPathEntries), optimizedDirectory,
                    suppressedExceptions));
        }

        if (suppressedExceptions.size() > 0) {
            Iterator suppressedExceptionsField = suppressedExceptions.iterator();

            while (suppressedExceptionsField.hasNext()) {
                IOException dexElementsSuppressedExceptions = (IOException)
                        suppressedExceptionsField.next();
                Log.w("MultiDex", "Exception in makeDexElement",
                        dexElementsSuppressedExceptions);
            }

            Field suppressedExceptionsField1 = findField(loader,
                    "dexElementsSuppressedExceptions");
            IOException[] dexElementsSuppressedExceptions1 = (IOException[]) ((IOException[])
                    suppressedExceptionsField1.get(loader));
            if (dexElementsSuppressedExceptions1 == null) {
                dexElementsSuppressedExceptions1 = (IOException[]) suppressedExceptions
                        .toArray(new IOException[suppressedExceptions.size()]);
            } else {
                IOException[] combined = new IOException[suppressedExceptions.size() +
                        dexElementsSuppressedExceptions1.length];
                suppressedExceptions.toArray(combined);
                System.arraycopy(dexElementsSuppressedExceptions1, 0, combined,
                        suppressedExceptions.size(), dexElementsSuppressedExceptions1.length);
                dexElementsSuppressedExceptions1 = combined;
            }

            suppressedExceptionsField1.set(loader, dexElementsSuppressedExceptions1);
        }

    }

    private static Object[] makeDexElements(Object dexPathList,
                                            ArrayList<File> files, File
                                                    optimizedDirectory,
                                            ArrayList<IOException> suppressedExceptions) throws
            IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Method makeDexElements = findMethod(dexPathList, "makeDexElements", new
                Class[]{ArrayList.class, File.class, ArrayList.class});
        return ((Object[]) makeDexElements.invoke(dexPathList, new Object[]{files,
                optimizedDirectory, suppressedExceptions}));
    }

    /**
     * A wrapper around
     * {@code private static final dalvik.system.DexPathList#makePathElements}.
     */
    private static Object[] makePathElements(
            Object dexPathList, ArrayList<File> files, File optimizedDirectory,
            ArrayList<IOException> suppressedExceptions)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

        Method makePathElements;
        try {
            makePathElements = findMethod(dexPathList, "makePathElements", List.class, File.class,
                    List.class);
        } catch (NoSuchMethodException e) {
            Log.e(TAG, "NoSuchMethodException: makePathElements(List,File,List) failure");
            try {
                makePathElements = findMethod(dexPathList, "makePathElements", ArrayList.class, File.class, ArrayList.class);
            } catch (NoSuchMethodException e1) {
                Log.e(TAG, "NoSuchMethodException: makeDexElements(ArrayList,File,ArrayList) failure");
                try {
                    Log.e(TAG, "NoSuchMethodException: try use v19 instead");
                    return V19.makeDexElements(dexPathList, files, optimizedDirectory, suppressedExceptions);
                } catch (NoSuchMethodException e2) {
                    Log.e(TAG, "NoSuchMethodException: makeDexElements(List,File,List) failure");
                    throw e2;
                }
            }
        }
        return (Object[]) makePathElements.invoke(dexPathList, files, optimizedDirectory, suppressedExceptions);
    }
}
