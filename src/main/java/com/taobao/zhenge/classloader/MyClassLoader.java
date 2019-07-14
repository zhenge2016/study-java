package com.taobao.zhenge.classloader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class MyClassLoader extends ClassLoader{

    static {
        System.out.println("加载MyClassLoader，classLoader为" + MyClassLoader.class.getClassLoader().getClass().getSimpleName());
    }

    private static String packageName = "com.taobao.zhenge.classloader";

    public MyClassLoader() {
        super();
        System.out.println("创建MyClassLoader对象，classLoader为" + this.getClass().getClassLoader().getClass().getSimpleName());
    }


    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if(!name.startsWith(packageName)) {
            return super.loadClass(name);
        }
        System.out.println("使用自定义classLoader加载" + name);
        byte[] data = loadClassData(name);
        return defineClass(name, data, 0, data.length);
    }

    private byte[] loadClassData(String name) {
        String fileName = name.substring(name.lastIndexOf(".") + 1) + ".class";
        byte[] data = null;
        try {
            InputStream in = this.getClass().getResourceAsStream(fileName);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            int b;
            while((b = in.read()) != -1) {
                out.write(b);
            }
            data = out.toByteArray();
        } catch (Exception e) {
            // ignore
        }
        return data;
    }

}
