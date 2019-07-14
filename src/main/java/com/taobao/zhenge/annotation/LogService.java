package com.taobao.zhenge.annotation;

import com.taobao.zhenge.annotation.model.LogTest1Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    private LogComponent logComponent;

    public void test() {
        LogTest1Param param = new LogTest1Param();
        logComponent.logTest2("abc", "1378998988");
        param.setName("abcde");
        param.setTel("111111111");
        logComponent.logTest1(param);

    }
}
