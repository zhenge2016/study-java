package com.taobao.zhenge.proxy;

import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Proxy;

public class Main {

    public static void main(String[] args) throws Exception {
        String className = WorkerImpl.class.getName();
        DynamicWorker dynamicWorker = new DynamicWorker(className);

        Worker worker = (Worker)Proxy.newProxyInstance(WorkerImpl.class.getClassLoader(), WorkerImpl.class.getInterfaces(), dynamicWorker);
        worker.work();
        worker.work2();

        System.out.println("worker是不是WorkerImpl的实例--->" + (worker instanceof WorkerImpl));

        System.out.println("worker对应的类为--->" + worker.getClass().getSimpleName());

        System.out.println("worker继承的父类为--->" + worker.getClass().getSuperclass().getSimpleName());

        System.out.println("worker实现的接口为--->" + StringUtils.join(worker.getClass().getInterfaces(), ","));

        System.out.println("worker所有的属性为--->\n" + StringUtils.join(worker.getClass().getDeclaredFields(), "\n"));

        System.out.println("worker所有的方法为--->\n" + StringUtils.join(worker.getClass().getDeclaredMethods(), "\n"));
    }
}
