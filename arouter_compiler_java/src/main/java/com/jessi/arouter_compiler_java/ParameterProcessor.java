package com.jessi.arouter_compiler_java;

import com.google.auto.service.AutoService;
import com.jessi.arouter_annotation_java.Parameter;
import com.jessi.arouter_compiler_java.utils.ProcessorConfig;
import com.jessi.arouter_compiler_java.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

/**
 * @author Created by CHEN on 2021/5/15
 * @email 188669@163.com
 */
@AutoService(Processor.class)// 开启
// 允许支持的注解类型
@SupportedAnnotationTypes({ProcessorConfig.PARAMETER_PACKAGE})
// 版本  
@SupportedSourceVersion(SourceVersion.RELEASE_7)

public class ParameterProcessor extends AbstractProcessor {

    private Elements elementUtils; // 类信息
    private Types typeUtils;  // 具体类型
    private Messager messager; // 日志
    private Filer filer; // 文件生成器

    private Map<TypeElement, List<Element>> tempParameterMap = new HashMap<>();
    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        elementUtils = processingEnv.getElementUtils();
        typeUtils = processingEnv.getTypeUtils();
        messager = processingEnv.getMessager();
        filer = processingEnv.getFiler();
        messager.printMessage(Diagnostic.Kind.WARNING, "  @Parameter注解出列器初始化  >>>>>>>>>  ");

    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        // 返回 true 会执行两次  false 执行一次
        messager.printMessage(Diagnostic.Kind.WARNING, "  @Parameter注解出列器开始处理  >>>>>>>>>  ");
        if (set.isEmpty()){
            messager.printMessage(Diagnostic.Kind.WARNING, "未检测到 @Parameter 注解\n");
            return false;
        }
        if (!ProcessorUtils.isEmpty(set)){
            // 获取所有被 Parameter 注解的属性集合
            Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(Parameter.class);
            if (!ProcessorUtils.isEmpty(elements)){
                for (Element element : elements){
                    // 字段节点的上一个节点 类节点
                    // 注解在属性上面，属性节点的父节点是类节点
                    //  enclosingElement == MainActiviy  也是 map 的key
                    TypeElement enclosingElement = (TypeElement) element.getEnclosingElement();
                    messager.printMessage(Diagnostic.Kind.WARNING, " 检测到 @Parameter注解  >>>>>>>>>  enclosingElement ");

                    // 根据属性所在类，缓存起来
                    if (tempParameterMap.containsKey(enclosingElement)){
                        tempParameterMap.get(enclosingElement).add(element);
                    }else {
                        List<Element> fields = new ArrayList<>();
                        fields.add(element);
                        tempParameterMap.put(enclosingElement, fields);
                    }
                }

                // todo 缓存完成，生成类文件
                // 判断是否需要生成类文件
                if (ProcessorUtils.isEmpty(tempParameterMap)) return true;

                TypeElement activityType = elementUtils.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
                // 要生成的类的要实现的接口
                TypeElement parameterType = elementUtils.getTypeElement(ProcessorConfig.AROUTER_AIP_PARAMETER_GET);

                // 生成方法参数
                //  Object targetParameter;
                ParameterSpec parameterSpec = ParameterSpec.builder(TypeName.OBJECT,ProcessorConfig.PARAMETER_NAME).build();

                // 遍历仓库 注解
                for (Map.Entry<TypeElement, List<Element>> entry : tempParameterMap.entrySet()){
                    TypeElement typeElement = entry.getKey();

                    // 注解只能用在 activity 里
                    // 如果父节点不是 activity 直接报错
                    if (!typeUtils.isSubtype(typeElement.asType(),activityType.asType())){
                        throw new RuntimeException("@Parameter注解目前仅限用于Activity类之上");
                    }

                    // 获取类名
                    // MainActivity
                    ClassName className = ClassName.get(typeElement);
                    // 生成方法
                    ParameterFactory factory = new ParameterFactory.Builder(parameterSpec)
                            .setMessager(messager)
                            .setElementUtils(elementUtils)
                            .setTypeUtils(typeUtils)
                            .setClassName(className)
                            .build();

                    // 添加第一行
                    // Personal_MainActivity t = (Personal_MainActivity) targetParameter;
                    factory.addFirstStatement();

                    // t.name = t.getIntent().getStringExtra("name");
                    for (Element element : entry.getValue()){
                        factory.buildStatement(element);
                    }
                    messager.printMessage(Diagnostic.Kind.WARNING, " 检测到 @Parameter注解  >>>>>>>>>  22222222222 ");

                    // 最终生成的类文件名 (类名$$Parameter)
                    String finalClassName = typeElement.getSimpleName() + ProcessorConfig.PARAMETER_FILE_NAME;

                    messager.printMessage(Diagnostic.Kind.NOTE, "APT生成获取参数类文件：" +
                            className.packageName() + "." + finalClassName);

                    try {
                        // 开始生成文件
                        JavaFile.builder(className.packageName(), // 包名
                                TypeSpec.classBuilder(finalClassName)   // 类名
                                .addModifiers(Modifier.PUBLIC)
                                .addSuperinterface(ClassName.get(parameterType)) // 实现 ParameterGet 接口
                                .addMethod(factory.build())
                                .build())   // 类构建完成
                            .build()    // JavaFile 构建完成
                            .writeTo(filer);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
        return true;
    }
}
