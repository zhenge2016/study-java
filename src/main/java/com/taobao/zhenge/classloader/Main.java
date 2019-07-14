package com.taobao.zhenge.classloader;


public class Main {

    static {
        System.out.println("加载Main，classLoader为" + Main.class.getClassLoader().getClass().getSimpleName());
    }

    public Main() {
        System.out.println("创建Main对象，classLoader为" + this.getClass().getClassLoader().getClass().getSimpleName());
        try {
            Class<ClassA> clazz = (Class<ClassA>)Class.forName("com.taobao.zhenge.classloader.ClassA");
            clazz.newInstance().test();
        } catch (Exception e) {
            // ignore
        }
        new ClassA().test();
        new Object();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("开始执行main方法");
        new MyClassLoader().loadClass("com.taobao.zhenge.classloader.Main").newInstance();
        System.out.println("结束执行main方法");
    }
}
