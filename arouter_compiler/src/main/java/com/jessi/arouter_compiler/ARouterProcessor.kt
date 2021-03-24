package com.jessi.arouter_compiler

import com.google.auto.service.AutoService
import com.jessi.arouter_annotation.ARouter
import com.jessi.arouter_compiler.utils.APT_PACKAGE
import com.jessi.arouter_compiler.utils.AROUTER_PACKAGE
import com.jessi.arouter_compiler.utils.OPTIONS
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic


// AutoService则是固定的写法，加个注解即可
// 通过auto-service中的@AutoService可以自动生成AutoService注解处理器，用来注册\
// 用来生成 META-INF/services/javax.annotation.processing.Processor 文件
@AutoService(Processor::class)

// 允许支持的注解类型，让注解处理器处理
@SupportedAnnotationTypes(AROUTER_PACKAGE)
// 指定 JDK 编译版本
@SupportedSourceVersion(SourceVersion.RELEASE_8)
// 注解处理器接收到的参数
@SupportedOptions(OPTIONS, APT_PACKAGE)
class ARouterProcessor : AbstractProcessor() {

    // message 用来打印日志相关信息
    private lateinit var message: Messager

    // 操作 Element 的工具类 (类，函数，属性，其实都是 Element)
    private lateinit var elementTool : Elements
    // type(类信息)的工具类，包含用于操作 TypeMirror 的方法
    private lateinit var typeTool : Types
    // 文件生成器 类 资源 等，就是最终要生成的文件 是需要Filer来完成的
    private lateinit var mFiler : Filer
    // app壳 传递过来的参数
    private var mOptions : String = ""


    override fun init(p0: ProcessingEnvironment?) {
        super.init(p0)
        p0?.apply {
            message = messager
            elementTool = elementUtils
            mFiler = filer

            mOptions = options[OPTIONS] ?: ""
        }
        val aptPackage =  p0?.options?.get(APT_PACKAGE) ?: ""
        message.printMessage(Diagnostic.Kind.NOTE, ">>>>>>>>>>>>>>>>>>>>>> options : $mOptions  aptPackage : $aptPackage")

        if (mOptions.isNotEmpty() && aptPackage.isNotEmpty()){
            message.printMessage(Diagnostic.Kind.NOTE, "APT 环境搭建完成.....")
        }else{
            message.printMessage(Diagnostic.Kind.NOTE, "APT 环境有问题，请检查 options 与 aptPackage 为空...")
        }
    }

    /**
     * 注解处理器的核心方法，处理具体的注解，生成Java文件
     *
     * @param p0 使用了支持处理注解的节点集合
     * @param p1  当前或之前的运行环境，可以通过该对象查找注解
     * @return  表示后续处理器不会再处理(已经处理完成)
     */
    override fun process(p0: MutableSet<out TypeElement>?, p1: RoundEnvironment?): Boolean {

        if (p0.isNullOrEmpty()){
            message.printMessage(Diagnostic.Kind.NOTE, "并没有发现 被@ARouter注解的地方")
            return false
        }
        // 遍历所有被 ARouter 注解的元素集合
        val elements = p1?.getElementsAnnotatedWith(ARouter::class.java)
        // 遍历所有节点
        elements?.forEach {
            // 获取类节点  获取包节点 包名
            val packageName = elementTool.getPackageOf(it).qualifiedName.toString()

            //获取类名
            val className = it.simpleName.toString()

            message.printMessage(Diagnostic.Kind.NOTE,
                ">>>>>>>>>>>>>> 被@ARetuer注解的类有： packageName ： $packageName   className ： $className"
            ) // 打印出 就证明APT没有问题

            /**
             * kotlinPoet 练习
             */
            kotlinPoet()

        }

        return true
    }

    private fun kotlinPoet() {
        /**
         * 文件路径为 com.example.helloworld.kotlin
         * 文件名问 HelloWorld.kt
         *
         *  fun main(args: Array<String>) {
                println("Hello, KotlinPoet!")
            }
         *
         */
        val funSpec = FunSpec.builder("main")
            .addModifiers(KModifier.FUN)
            .returns(Unit.javaClass)
            .addParameter("args", Array<String>::class)
            .addStatement("println(%S)", "Hello, KotlinPoet!")
            .build()
    }
}

