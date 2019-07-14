package com.taobao.zhenge.annotation;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = {"com.taobao.zhenge.annotation"})
@EnableAspectJAutoProxy
public class AnnotationSpringConfig {
}
