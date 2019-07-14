package com.taobao.zhenge.annotation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

@Aspect
@Component
public class LogAspect {

    private final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Around("@annotation(logTrace)")
    public Object logFor(final ProceedingJoinPoint joinPoint, LogTrace logTrace) throws Throwable {

        long startTime = System.currentTimeMillis();
        Object[] params = joinPoint.getArgs();
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        String className = methodSignature.getMethod().getDeclaringClass().getSimpleName();
        String method = methodSignature.getMethod().getName();
        String bizCode = parseBizCode(params, logTrace);

        try {
            Object result = joinPoint.proceed();
            logSuccess(bizCode, className, method, params, result, System.currentTimeMillis() - startTime);
            return result;
        } catch (Throwable t) {
            logException(bizCode, className, method, params, System.currentTimeMillis() - startTime, t);
            throw t;
        }
    }

    private String parseBizCode(Object[] params, LogTrace logTrace) {
        try {
            String bizCodeTemp = logTrace.bizCode();
            if(StringUtils.isBlank(bizCodeTemp)) {
                return "";
            }
            // 只支持取第n个参数，或者第n个参数的某一个属性，更深层次的暂时不支持
            if(bizCodeTemp.startsWith("#")) {
                String[] path = bizCodeTemp.substring(1).split("\\.");

                if(path.length == 1) {
                    String path1 = path[0];
                    if(path1.startsWith("[") && path1.endsWith("]")) {
                        int i = Integer.parseInt(path1.substring(1, path1.length()-1));
                        Object param = params[i];
                        return param.toString();
                    } else {
                        throw new RuntimeException("not support biz code temp!");
                    }
                } else if(path.length == 2) {
                    String path1 = path[0];
                    String path2 = path[1];
                    if(path1.startsWith("[") && path1.endsWith("]")) {
                        int i = Integer.parseInt(path1.substring(1, path1.length()-1));
                        Object param = params[i];
                        Field field = param.getClass().getDeclaredField(path2);
                        field.setAccessible(true);
                        Object bizCodeObj = field.get(param);
                        return bizCodeObj.toString();
                    } else {
                        throw new RuntimeException("not support biz code temp!");
                    }
                } else {
                    throw new RuntimeException("not support biz code temp!");
                }

            }
        } catch (Throwable t) {
            logger.error("parse biz code error!", t);
            return "";
        }
        return "";
    }

    private void logSuccess(String bizCode, String className, String methodName, Object[] args, Object result, long costTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.error(sdf.format(new Date()) + "|" + bizCode + "|" + className + "|" + methodName + "|" +
                JSON.toJSONString(args, SerializerFeature.IgnoreNonFieldGetter) + "|" +
                JSON.toJSONString(result, SerializerFeature.IgnoreNonFieldGetter) + "|" + costTime);
    }

    private void logException(String bizCode, String className, String methodName, Object[] args, long costTime, Throwable t) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.error(sdf.format(new Date()) + "|" + bizCode + "|" + className + "|" + methodName + "|" +
                JSON.toJSONString(args, SerializerFeature.IgnoreNonFieldGetter) + "|" + costTime, t);
    }
}
