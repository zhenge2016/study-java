package com.taobao.zhenge.java.classloader;

public class ClassA {

    static {
        System.out.println("ClassA，classLoader为" + ClassA.class.getClassLoader().getClass().getSimpleName());
    }

    public ClassA() {
        System.out.println("创建ClassA对象，classLoader为" + this.getClass().getClassLoader().getClass().getSimpleName());
    }

    public void test() {
        System.out.println("开始测试!");
    }
}
