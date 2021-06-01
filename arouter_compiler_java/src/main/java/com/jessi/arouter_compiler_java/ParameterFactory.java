package com.jessi.arouter_compiler_java;

import com.jessi.arouter_annotation_java.Parameter;
import com.jessi.arouter_compiler_java.utils.ProcessorConfig;
import com.jessi.arouter_compiler_java.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.annotation.processing.Messager;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

import jdk.vm.ci.code.site.Call;

/**
 * @author Created by CHEN on 2021/5/18
 * @email 188669@163.com

 @Override
 public void getParameter(Object targetParameter) {
     Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     t.name = t.getIntent().getStringExtra("name");
     t.sex = t.getIntent().getStringExtra("sex");
 }
 */
public class ParameterFactory {

    // 方法的构建
    private MethodSpec.Builder method;
    // 类名
    private ClassName className;

    private Messager messager;

    //获取元素接口信息，（生成类文件需要接口实现类）
    private TypeMirror callMirror;
    // type(类信息)工具类 包含用于操作 typeMirror 的工具方法
    private Types typeUtils;


    private ParameterFactory(Builder builder){
        messager = builder.messager;
        className = builder.className;
        typeUtils = builder.typeUtils;

        // 通过此方法
        // 通过方法参数体构建方法体
        method = MethodSpec.methodBuilder(ProcessorConfig.PARAMETER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Override.class)
                .addParameter(builder.parameterSpec);

        callMirror = builder.elementUtils.getTypeElement(ProcessorConfig.CALL)
                .asType();
    }

    /**
     * 添加方法 内容
     *  Personal_MainActivity t = (Personal_MainActivity) targetParameter;
     */
    public void addFirstStatement(){
        method.addStatement("$T t = ($T)"+ ProcessorConfig.PARAMETER_NAME, className, className);
    }

    public MethodSpec build(){
        return method.build();
    }

    /**
     * 方法添加内容
     * t.name = t.getIntent().getStringExtra("name");
     */
    public void buildStatement(Element element){
        // 遍历注解的属性节点，生成函数体
        TypeMirror typeMirror = element.asType();

        // 获取 typeKind 枚举类型的序列号
        int type = typeMirror.getKind().ordinal();

        // 获取属性能  如：name
        String fieldName = element.getSimpleName().toString();
        // 获取注解的值
        String annotationValue = element.getAnnotation(Parameter.class).name();

        // 如果注解值为空的,则使用属性名
        annotationValue = ProcessorUtils.isEmpty(annotationValue) ? fieldName : annotationValue;

        // 开始拼接
        String finalValue = "t." + fieldName;

        // t.getIntent().
        String methodContent = finalValue + " = t.getIntent().";

        // 根据TypeKind 类型拼接后面
        if (type == TypeKind.INT.ordinal()){
            // t.s = t.getIntent().getIntExtra("age", t.age);
            methodContent += "getIntExtra($S, " + finalValue +")";
        }else if (type == TypeKind.BOOLEAN.ordinal()){
            methodContent += "getBooleanExtra($S, " + finalValue +")";
        }else {
            if (typeMirror.toString().equalsIgnoreCase(ProcessorConfig.STRING)){
                // string 类型
                methodContent += "getStringExtra($S)";
            }else if (typeUtils.isSubtype(typeMirror, callMirror)){

                // 如果属性是实现了Call接口
                // t.orderDrawable = (OrderDrawable) RouterManager.getInstance().build("/order/getDrawable").navigation(t);
                methodContent = finalValue + " = ($T)$T.getInstance().build($S).navigation(t)";

                method.addStatement(methodContent,
                        TypeName.get(typeMirror),
                        ClassName.get(ProcessorConfig.AROUTER_API_PACKAGE, ProcessorConfig.ROUTER_MANAGER),
                        annotationValue);
                messager.printMessage(Diagnostic.Kind.WARNING, " 检测到 @Parameter注解  >>>>>>>>>  333333333 ");

                return;
            }else {
                // t.s = getIntent().getParcelableExtra("name")
                methodContent = "t.getIntent().getParcelableExtra($S)";
            }
        }
        if (methodContent.contains("Parcelable")){
            // Parcelable 需要强转 t.userinfo = (UserInfo)t.getIntent().getParcelableExtra("user");
            method.addStatement(finalValue + " = ($T)"+methodContent, ClassName.get(element.asType()), fieldName);
        } else if (methodContent.endsWith(")")){
            // t.age = t.getIntent().getBooleanExtra("age", t.age ==  9);
            method.addStatement(methodContent, annotationValue);
        }else {
            messager.printMessage(Diagnostic.Kind.ERROR, "目前暂支持String、int、boolean传参");
        }
    }




    public static class Builder{
        // messager
        private Messager messager;
        // 类名 如 : Activity
        private ClassName className;
        // 方法参数体
        private ParameterSpec parameterSpec;
        // 操作 elements 工具类，（类，函数，属性都是Element）
        private Elements elementUtils;
        // type(类信息)工具类 包含用于操作 typeMirror 的工具方法
        private Types typeUtils;

        public Builder(ParameterSpec parameterSpec){
            this.parameterSpec = parameterSpec;
        }

        public Builder setMessager(Messager messager){
            this.messager = messager;
            return this;
        }

        public Builder setClassName(ClassName className){
            this.className = className;
            return this;
        }

        public Builder setTypeUtils(Types typeUtils){
            this.typeUtils = typeUtils;
            return this;
        }

        public Builder setElementUtils(Elements elementUtils){
            this.elementUtils = elementUtils;
            return this;
        }

        public ParameterFactory build(){
            if (parameterSpec == null){
                messager.printMessage(Diagnostic.Kind.ERROR, "parameterSpec 方法参数体为空");
            }
            if (className == null) {
                throw new IllegalArgumentException("方法内容中的className为空");
            }

            if (messager == null) {
                throw new IllegalArgumentException("messager为空，Messager用来报告错误、警告和其他提示信息");
            }
            return new ParameterFactory(this);
        }

    }
}
