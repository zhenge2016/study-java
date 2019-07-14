package com.taobao.zhenge.annotation;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LogTraceTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(AnnotationSpringConfig.class);
        applicationContext.getBean(LogService.class).test();
    }
}
