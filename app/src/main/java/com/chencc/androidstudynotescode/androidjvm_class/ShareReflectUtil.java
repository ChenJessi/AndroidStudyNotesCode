package com.chencc.androidstudynotescode.androidjvm_class;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ShareReflectUtil {

    /**
     * 从  instance 到其父类，查找 name属性
     * @param instance
     * @param name
     */
    public static Field findField(Object instance, String name) throws NoSuchFieldException {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()){
            try {
                Field field = clazz.getDeclaredField(name);
                if (!field.isAccessible()){
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        throw  new NoSuchFieldException("Field " + name + " not found in " + instance.getClass());
    }

    public static Method findMethod(Object object, String name , Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()){
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        throw new NoSuchMethodException("Method "
                + name
                + " with parameters "
                + Arrays.asList(parameterTypes)
                + " not found in " + object.getClass());
    }
}
