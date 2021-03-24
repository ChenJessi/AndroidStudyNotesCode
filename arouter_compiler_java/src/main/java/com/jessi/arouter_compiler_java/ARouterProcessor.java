package com.jessi.arouter_compiler_java;

import com.google.auto.service.AutoService;
import com.jessi.arouter_annotation_java.ARouter;
import com.jessi.arouter_compiler_java.utils.ProcessorConfig;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
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
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
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
    // app壳 传递过来的参数
    private String options;
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
        elementTool = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();

        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        String aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);

        messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   options : " + options +"\n");
        messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>   aptPackage : " + aptPackage + "\n");

        // 只有接受到 App壳 传递过来的书籍，才能证明我们的 APT环境搭建完成
        if (options != null && aptPackage != null){
            messager.printMessage(Diagnostic.Kind.WARNING, "APT 环境搭建完成......\n");
        }else {
            messager.printMessage(Diagnostic.Kind.WARNING, "APT 环境有问题，请检查 options 与 aptPackage 为 null...\n");
        }
    }

    /**
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param set 使用了支持处理注解的节点集合
     * @param roundEnvironment  当前或之前的运行环境，可以通过该对象查找注解
     * @return  表示后续处理器不会再处理(已经处理完成)
     */
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        if (set.isEmpty()){
            messager.printMessage(Diagnostic.Kind.WARNING, "未检测到 @ARouter 注解\n");
            return false;
        }

        // 获取所有被 @ARouter 注解的元素集合
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(ARouter.class);
        // 遍历所有类节点
        for (Element element : elements){

            // 获取类节点 获取节点包名
            String packageName = elementTool.getPackageOf(element).getQualifiedName().toString();
            // 获取简单类名
            String className = element.getSimpleName().toString();

            messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> 被@ARetuer注解的类有：" + packageName + "  " +className ); // 打印出 就证明APT没有问题

            /**
             *  JavaPoet 练习
             */
            JavaPoetTest();

        }

        return true;
    }


    /**
     * JavaPoet 练习
     */
    private void JavaPoetTest(){
        /**
         * package com.example.helloworld;
         *
         * public final class HelloWorld {
         *   public static void main(String[] args) {
         *     System.out.println("Hello, JavaPoet!");
         *   }
         * }
         */

        // 方法
        MethodSpec mainMethod = MethodSpec.methodBuilder("main")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(void.class)
                .addParameter(String[].class, "args")
                .addStatement("$T.out.println($S)", System.class, "Hello, JavaPoet!")
                .build();
        // 类
        TypeSpec helloWorld = TypeSpec.classBuilder("HelloWorld")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(mainMethod)
                .build();
        // 包
        JavaFile packagef = JavaFile.builder("com.example", helloWorld).build();

        // 去生成
        try {
            packagef.writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.WARNING, "生成失败，请检查代码...");
        }
    }
}