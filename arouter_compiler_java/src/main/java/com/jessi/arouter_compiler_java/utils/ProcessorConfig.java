package com.jessi.arouter_compiler_java.utils;



public interface ProcessorConfig {

    // @ARouter注解 的 包名 + 类名
    String AROUTER_PACKAGE =  "com.jessi.arouter_annotation_java.ARouter";

    // 接收参数的TAG标记
    String OPTIONS = "moduleName"; // 目的是接收 每个module名称
    String APT_PACKAGE = "packageNameForAPT"; // 目的是接收 包名（APT 存放的包名）
}
