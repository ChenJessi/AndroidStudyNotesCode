package com.jessi.arouter_compiler_java.utils;

/**
 * @author Created by CHEN on 2021/4/26
 * @email 188669@163.com
 */
public final class ProcessorUtils {
    public static boolean isEmpty(CharSequence cs) {
        return cs == null || cs.length() == 0;
    }

    public static boolean isEmpty(Collection<?> coll) {
        return coll == null || coll.isEmpty();
    }

    public static boolean isEmpty(final Map<?, ?> map) {
        return map == null || map.isEmpty();
    }
}
