package com.taobao.zhenge.annotation;

import com.taobao.zhenge.annotation.model.LogTest1Param;
import org.springframework.stereotype.Component;


@Component
public class LogComponent {

    @LogTrace(bizCode = "#[0].tel")
    public String logTest1(LogTest1Param param) {
        return "logTest1";
    }

    @LogTrace(bizCode = "#[1]")
    public String logTest2(String name, String tel) {
        return "logTest2";
    }
}
