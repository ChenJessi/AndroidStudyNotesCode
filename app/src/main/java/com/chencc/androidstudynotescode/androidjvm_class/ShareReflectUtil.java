package com.chencc.androidstudynotescode.androidjvm_class;

import java.io.File;
import java.lang.reflect.Array;
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

    /**
     *从 object 到其父类，查找 name 方法
     * @param object
     * @param name
     * @param parameterTypes
     * @return
     * @throws NoSuchMethodException
     */
    public static Method findMethod(Object object, String name , Class<?>... parameterTypes) throws NoSuchMethodException {
        for (Class<?> clazz = object.getClass(); clazz != null; clazz = clazz.getSuperclass()){
            try {
                Method method = clazz.getDeclaredMethod(name, parameterTypes);
                return method;
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


    /**
     *
     * @param object
     * @param fieldName
     * @param patchElements
     */
    public static void expandFieldArray(Object object, String fieldName, Object[] patchElements) throws NoSuchFieldException, IllegalAccessException {
        // 拿到 classloader 中的 dexElements 数组
        Field dexElementsField = findField(object, fieldName);
        // old elemtnts
        Object[] dexElements = (Object[]) dexElementsField.get(object);

        // 合并后的数组
        Object[] newElements = (Object[]) Array.newInstance(dexElements.getClass().getComponentType(), dexElements.length + patchElements.length);

        // 先拷贝新数组
        System.arraycopy(patchElements, 0, newElements, 0, patchElements.length);
        System.arraycopy(dexElements, 0, newElements, patchElements.length,  dexElements.length );

        // 修改 classloader 中 pathList 的 elements
        dexElementsField.set(object, newElements);
    }
}
