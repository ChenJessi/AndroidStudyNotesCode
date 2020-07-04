package com.chencc.androidstudynotescode.jvm;


/**
 * 对象的自我拯救
 * finalize() 函数
 */
public class FinalizeGC {
    public static FinalizeGC instance = null;
    public void isAlive(){
        System.out.println("I am still alive!");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed");
        FinalizeGC.instance = this;
    }

    public static void main(String[] args) throws Throwable{
        instance = new FinalizeGC();
        // 对象进行第一次 gc
        instance = null;
        System.gc();
//        Thread.sleep(1000);//Finalizer方法优先级很低，需要等待
        if(instance != null){
            instance.isAlive();
        }else{
            System.out.println("I am dead !");
        }
        //对象进行第2次GC
        instance = null;
        System.gc();
//        Thread.sleep(1000);
        if(instance != null){
            instance.isAlive();
        }else{
            System.out.println("I am dead !");
        }
    }
}
