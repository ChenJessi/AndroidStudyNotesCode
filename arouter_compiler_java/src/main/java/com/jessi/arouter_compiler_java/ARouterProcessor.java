package com.jessi.arouter_compiler_java;

import com.google.auto.service.AutoService;
import com.jessi.arouter_annotation_java.ARouter;
import com.jessi.arouter_annotation_java.bean.RouterBean;
import com.jessi.arouter_compiler_java.utils.ProcessorConfig;
import com.jessi.arouter_compiler_java.utils.ProcessorUtils;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.WildcardTypeName;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

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
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.WildcardType;
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

    // 缓存一
    // key：group   value：List<RouterBean>
    private Map<String, List<RouterBean>> mAllPathMap = new HashMap<>();
    // 仓库二 Group 缓存二
    // Map<"personal", "ARouter$$Path$$personal.class">
    private Map<String, String> mAllGroupMap = new HashMap<>();
    private String aptPackage; // 各个模块传递过来的目录 用于统一存放 apt生成的文件
    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);

        messager = processingEnvironment.getMessager();
        elementTool = processingEnvironment.getElementUtils();
        filer = processingEnvironment.getFiler();
        typeTool = processingEnvironment.getTypeUtils();

        options = processingEnvironment.getOptions().get(ProcessorConfig.OPTIONS);
        aptPackage = processingEnvironment.getOptions().get(ProcessorConfig.APT_PACKAGE);

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

        // 通过 element工具类，获取Activity Callback 类型
        TypeElement activityType = elementTool.getTypeElement(ProcessorConfig.ACTIVITY_PACKAGE);
        TypeElement callType = elementTool.getTypeElement(ProcessorConfig.CALL);
        // 显示类信息，（获取被注解的节点，类节点）也叫自描述
        TypeMirror activityMirror = activityType.asType();
        TypeMirror callMirror = callType.asType();

        // 遍历所有类节点
        for (Element element : elements){

            // 获取类节点 获取节点包名
            String packageName = elementTool.getPackageOf(element).getQualifiedName().toString();
            // 获取简单类名
            String className = element.getSimpleName().toString();

            messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> 被@ARetuer注解的类有：" + packageName + "  " +className ); // 打印出 就证明APT没有问题

//            /**
//             *  JavaPoet 练习
//             */
//            JavaPoetTest();
            // 获取注解
            ARouter aRouter = element.getAnnotation(ARouter.class);

            messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> 拿到 @ARetuer 注解：" + aRouter  );


            // 封装路由对象
            RouterBean routerBean = new RouterBean.Builder()
                    .addGroup(aRouter.group())
                    .addPath(aRouter.path())
                    .addElement(element)
                    .build();

            // 校验
            // ARouter 注解类必须继承 Activity
            TypeMirror typeMirror = element.asType();
            if (typeTool.isSubtype(typeMirror, activityMirror)){
                // 是activity
                routerBean.setTypeEnum(RouterBean.TypeEnum.ACTIVITY);
                messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> s是   activity" ); // 打印出 就证明APT没有问题
            }else if (typeTool.isSubtype(typeMirror, callMirror)){
                routerBean.setTypeEnum(RouterBean.TypeEnum.CALL);
                messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> s是   call" ); // 打印出 就证明APT没有问题

            } else {
                // 不匹配抛出异常
                messager.printMessage(Diagnostic.Kind.WARNING, ">>>>>>>>>>>>>> s是  ================ activity" ); // 打印出 就证明APT没有问题

                throw new RuntimeException("@ARouter注解目前仅限用于Activity类之上");
            }

            if (checkRouterPath(routerBean)){
                messager.printMessage(Diagnostic.Kind.NOTE, "RouterBean Check Success:" + routerBean.toString());

                // 赋值到 mAllPathMap 缓存集合
                List<RouterBean> routerBeans = mAllPathMap.get(routerBean.getGroup());

                if (ProcessorUtils.isEmpty(routerBeans)){
                    routerBeans = new ArrayList<>();
                    routerBeans.add(routerBean);
                    mAllPathMap.put(routerBean.getGroup(), routerBeans);
                }else {
                    routerBeans.add(routerBean);
                }
            }else {
                messager.printMessage(Diagnostic.Kind.ERROR,"@ARouter注解未按规范配置，如：/app/MainActivity");
            }

        } // 缓存完毕

        // ARouterPath 描述
        TypeElement pathType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_PATH);
        // ARouterGroup描述
        TypeElement groupType = elementTool.getTypeElement(ProcessorConfig.AROUTER_API_GROUP);


        messager.printMessage(Diagnostic.Kind.NOTE, " >>>>>>>>>>   ARouterPath 描述  ：" + pathType + " "   + ProcessorConfig.AROUTER_API_PATH);
        messager.printMessage(Diagnostic.Kind.NOTE, " >>>>>>>>>>   ARouterGroup 描述  ：" + groupType + "  "  +ProcessorConfig.AROUTER_API_GROUP);

        /**
         * 第一步，生成 Path 类
         */
        try {
            createPathFile(pathType);
        } catch (Exception e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "在生成PATH模板时，异常了 e:" + e.getMessage());
        }

        /**
         * 生成 group 类
         */
        try {
            createGroupFile(groupType, pathType);
        } catch (Exception e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "在生成 Group 模板时，异常了 e:" + e.getMessage());
        }

        return true;
    }

    /**
     * 校验 @ARouter 注解的值，如果group未填写就从必填项 path 中截取
     * @param routerBean 路由详细信息，最终实体封装类
     * @return
     */
    private boolean checkRouterPath(RouterBean bean){
        String group = bean.getGroup();
        String path = bean.getPath();
        // 校验
        // @ARouter 注解中的path值，必须要以 / 开头
        if (ProcessorUtils.isEmpty(path) || !path.startsWith("/")){
            messager.printMessage(Diagnostic.Kind.ERROR,  "@ARouter注解中的path值，必须要以 / 开头");
            return false;
        }

        if (path.lastIndexOf("/") == 0){
            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解未按规范配置，如：/app/MainActivity");
            return false;
        }
        // 从第一个 / 到第二个 / 中间截取，如：/app/MainActivity 截取出 app  作为group
        String finalGroup = path.substring(1, path.indexOf("/", 1));


        // @ARouter 注解中的group有赋值
//        if(!ProcessorUtils.isEmpty(group) && group.equals(options)){
//            messager.printMessage(Diagnostic.Kind.ERROR, "@ARouter注解中的group值必须和子模块名一致！");
//            return false;
//        }else {
//            bean.setGroup(finalGroup);
//        }
        if (ProcessorUtils.isEmpty(group)){
            bean.setGroup(finalGroup);
        }
        // group 没问题，返回true
        return true;
    }


    /**
     * 生成 Path 类
     * @param pathType
    Path 类预期效果
    public class ARouter$$Path$$personal implements ARouterPath {
        @Override
        public Map<String, RouterBean> getPathMap() {
            Map<String, RouterBean> pathMap = new HashMap<>();
            pathMap.put("/personal/Personal_Main2Activity", RouterBean.create();
            pathMap.put("/personal/Personal_MainActivity", RouterBean.create());
            return pathMap;
        }
    }
     */
    private void createPathFile(TypeElement pathType){
        // 判断map仓库，是否有需要生成的文件
        if (ProcessorUtils.isEmpty(mAllPathMap)){
            return;
        }

        // Map<String, RouterBean>
        TypeName methodReturn = ParameterizedTypeName.get(
                ClassName.get(Map.class), // Map
                ClassName.get(String.class), // Map<String
                ClassName.get(RouterBean.class) // Map<String, RouterBean>
        );

        // 遍历仓库
        for (Map.Entry<String, List<RouterBean>> entry : mAllPathMap.entrySet()){
            // 先生成方法
            MethodSpec.Builder methodBuilder = MethodSpec.methodBuilder(ProcessorConfig.PATH_METHOD_NAME)
                    .addAnnotation(Override.class)  // 添加注解
                    .addModifiers(Modifier.PUBLIC)  // public 方法
                    .returns(methodReturn);         // 返回 Map<String, RouterBean> 类型

            // 给方法添加内容
            // Map<String, RouterBean> pathMap = new HashMap<>();
            // $N : 变量  $T 类型
            methodBuilder.addStatement("$T<$T,$T> $N = new $T<>()",
                    ClassName.get(Map.class),           // Map
                    ClassName.get(String.class),        // Map<String
                    ClassName.get(RouterBean.class),    // Map<String, RouterBean>
                    ProcessorConfig.PATH_VAR1,          // Map<String, RouterBean> pathMap
                    ClassName.get(HashMap.class));      // Map<String, RouterBean> pathMap = new HashMap()

            // 每个组有多个path
            List<RouterBean> pathList = entry.getValue();
            // pathMap.put("/personal/Personal_MainActivity", RouterBean.create(RouterBean.TypeEnum.ACTIVITY, class, path,group));
            for (RouterBean bean : pathList){
                methodBuilder.addStatement("$N.put($S, $T.create($T.$L, $T.class, $S, $S))",
                        ProcessorConfig.PATH_VAR1, // pathMap.put
                        bean.getPath(), // path : /personal/Personal_MainActivity
                        ClassName.get(RouterBean.class),    // Router.create
                        ClassName.get(RouterBean.TypeEnum.class),  // RouterBean.Type
                        bean.getTypeEnum(),     // 枚举类型  ACTIVITY
                        ClassName.get((TypeElement)bean.getElement()),  // MainActivity.class
                        bean.getPath(),
                        bean.getGroup());
            }

            // return pathMap
            methodBuilder.addStatement("return $N", ProcessorConfig.PATH_VAR1);

            // 因为类中有 implements 所以方法和类要一起生成，
            // 最终生成的类文件名 ARouter$$Path$$ + 组名
            String finalClassName = ProcessorConfig.PATH_FILE_NAME + entry.getKey();

            messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由Path类文件：" +
                    aptPackage + "." + finalClassName);

            try {
                // 生成类文件 ARouter$$Path$$ + 组名
                JavaFile.builder(aptPackage,    // 包名
                        TypeSpec.classBuilder(finalClassName)// 类名
                        .addSuperinterface(ClassName.get(pathType))  // 实现ARouterLoadPath接口  implements ARouterPath==pathType
                        .addModifiers(Modifier.PUBLIC)      // 添加修饰符
                        .addMethod(methodBuilder.build())   // 添加方法
                        .build())   //类构建完成
                    .build()  // JavaFile 文件构建完成
                    .writeTo(filer); // 文件生成器开始生成类文件

                // 缓存 path 路径文件生成之后，才能赋值路由组mAllGroupMap
                mAllGroupMap.put(entry.getKey(), finalClassName);
            } catch (IOException e) {
                e.printStackTrace();
                messager.printMessage(Diagnostic.Kind.NOTE, "在生成PATH模板时，异常了 e:" + e.getMessage());
            }
        }
    }

    /**
     * 生成group 类
     * @param groupType
    Group类 预期效果
    public class ARouter$$Group$$personal implements ARouterGroup {
        @Override
        public Map<String, Class<? extends ARouterPath>> getGroupMap() {
            Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
            groupMap.put("personal", ARouter$$Path$$personal.class);
            return groupMap;
        }
    }
     */
    private void createGroupFile(TypeElement groupType, TypeElement pathType){
        // 仓库二 判断是否有需要生成的类文件
        if (ProcessorUtils.isEmpty(mAllGroupMap) || ProcessorUtils.isEmpty(mAllPathMap)) return;


        // 返回值 这一段 Map<String, Class<? extends ARouterPath>>
        TypeName methodReturns = ParameterizedTypeName.get(
                ClassName.get(Map.class),  // Map
                ClassName.get(String.class),  // Map<String

                // Class<? extends ARouterPath>
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        // ? extends ARouterPath
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))) // ? extends ARouterLoadPath
                // WildcardTypeName.supertypeOf()  ? super

                // 最终的：Map<String, Class<? extends ARouterPath>>
        );

        // 1. 方法 public Map<String, Class<? extends ARouterPath>> getGroupMap() {
        MethodSpec.Builder methodBuidler = MethodSpec.methodBuilder(ProcessorConfig.GROUP_METHOD_NAME)
                .addAnnotation(Override.class)  // 重写注解 @Override
                .addModifiers(Modifier.PUBLIC)  // public 关键字
                .returns(methodReturns); // 返回值

        // Map<String, Class<? extends ARouterPath>> groupMap = new HashMap<>();
        methodBuidler.addStatement("$T<$T, $T> $N = new $T<>()",
                ClassName.get(Map.class),
                ClassName.get(String.class),

                // Class<? extends ARouterPath> 难度
                ParameterizedTypeName.get(ClassName.get(Class.class),
                        WildcardTypeName.subtypeOf(ClassName.get(pathType))),  // ? extends ARouterPath

                ProcessorConfig.GROUP_VAR1, // 变量名
                ClassName.get(HashMap.class));

        //  groupMap.put("personal", ARouter$$Path$$personal.class);
        //	groupMap.put("order", ARouter$$Path$$order.class);

        for (Map.Entry<String, String> entry : mAllGroupMap.entrySet()){
            methodBuidler.addStatement("$N.put($S, $T.class)",
                    ProcessorConfig.GROUP_VAR1,   // groupMap
                    entry.getKey(), // 组名
                    ClassName.get(aptPackage, entry.getValue())); // 组对应的类名
        }

        // return groupMap;
        methodBuidler.addStatement("return $N", ProcessorConfig.GROUP_VAR1);

        // 最终生成的类文件名 ARouter$$Group$$ + personal
        String finalClassName = ProcessorConfig.GROUP_FILE_NAME + options;

        messager.printMessage(Diagnostic.Kind.NOTE, "APT生成路由组Group类文件：" +
                aptPackage + "." + finalClassName);

        // 生成类文件：ARouter$$Group$$app
        try {
            JavaFile.builder(aptPackage, // 包名
                    TypeSpec.classBuilder(finalClassName) // 类名
                    .addModifiers(Modifier.PUBLIC)  // public 类
                    .addSuperinterface(ClassName.get(groupType)) // 实现接口 implements ARouterGroup
                    .addMethod(methodBuidler.build())  // 方法构建
                    .build())   // 类构建完成
                    .build()    // 文件构建完成
                    .writeTo(filer);    // 文件生成器开始生成类文件
        } catch (IOException e) {
            e.printStackTrace();
            messager.printMessage(Diagnostic.Kind.NOTE, "在生成Group模板时，异常了 e:" + e.getMessage());

        }
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