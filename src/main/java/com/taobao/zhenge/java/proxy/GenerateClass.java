package com.taobao.zhenge.java.proxy;

import sun.misc.ProxyGenerator;

import java.io.FileOutputStream;
import java.lang.reflect.Modifier;

public class GenerateClass {

    public static void main(String[] args) throws Exception {
        String className = "com.taobao.test.ProxyClass";
        Class[] interfaces = new Class[]{Worker.class};
        int accessFlags = Modifier.PUBLIC | Modifier.FINAL;

        byte[] classByte = ProxyGenerator.generateProxyClass(className, interfaces, accessFlags);

        FileOutputStream fos = new FileOutputStream("ProxyClass");
        fos.write(classByte);
        fos.close();
    }

}
