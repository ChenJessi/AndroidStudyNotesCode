package com.jessi.arouter_compiler_java;

import com.google.auto.service.AutoService;
import com.jessi.arouter_compiler_java.utils.ProcessorConfig;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedOptions;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;


// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册\
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor.class)

// 允许支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes({ProcessorConfig.AROUTER_PACKAGE})

// 指定 JDK 编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_7)

// 注解处理器接收的参数
@SupportedOptions({ ProcessorConfig.OPTIONS, ProcessorConfig.APT_PACKAGE})
public class ARouterProcessor extends AbstractProcessor {

    // message 用来打印日志相关信息
    private Messager messager;
    // 操作 Elenents 的工具类(类，函数，属性，其实都是 element)
    private Elements elementTool;
    // type (类信息)的工具类，包含用于操作的 TypeMirror 的工具方法
    private Types typeTool;

    // 文件生成器，类，资源等， 就是最终要生成的文件，是需要 filer 来完成的
    private Filer filer;

    private String options;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
        elementTool = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();

        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        String aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);

        messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   options : " + options);
        messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   aptPackage : " + aptPackage);
        if (options != null && aptPackage != null){
            messager.printMessage(Diagnostic.Kind.WARNING, "APT 环境搭建完成......");
        }else {
            messager.printMessage(Diagnostic.Kind.WARNING, "APT 环境有问题，请检查 options 与 aptPackage 为 null...");
        }
    }

    /**
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set 使用了支持处理注解的节点集合
     * @param roundEnvironment
     * @return
     */
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        messager.printMessage(Diagnostic.Kind.WARNING, "检测到 ARouter 注解");



        return true;
    }
}