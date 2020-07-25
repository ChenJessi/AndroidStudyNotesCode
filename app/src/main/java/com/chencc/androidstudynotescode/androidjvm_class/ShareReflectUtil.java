package com.chencc.androidstudynotescode.androidjvm_class;

import java.io.File;
import java.lang.reflect.Field;

public class ShareReflectUtil {

    /**
     * 从  instance 到其父类，查找 name属性
     * @param instance
     * @param name
     */
    public static Field findField(Object instance, String name){
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

        throw
    }
}
