package com.taobao.zhenge.java.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class DynamicWorker implements InvocationHandler {

    private Object obj;

    public DynamicWorker(Object obj) {
        this.obj = obj;
    }

    public DynamicWorker(Class clazz) throws Exception {
        this(clazz.newInstance());
    }

    public DynamicWorker(String className) throws Exception {
        this(Class.forName(className));
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("开始工作之前，我要准备工具！");
        method.invoke(obj, args);
        System.out.println("完成工作之后，收拾好工具！");
        return null;
    }
}
