package com.example.demo.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglong
 * @date 2018.3.19
 */
public class RealData implements Callable<String> {
    private String Data;

    public RealData(String Data) {
        this.Data = Data;
    }

    @Override
    public String call() throws Exception {
        //利用sleep来表示任务处理
        TimeUnit.SECONDS.sleep(5);
        return "这是处理"+Data+"结果";

    }


}
