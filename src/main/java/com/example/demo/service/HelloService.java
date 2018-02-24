package com.example.demo.service;

import com.example.demo.model.HelloWorld;
import org.joda.time.DateTime;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {

    public List<HelloWorld> helloWorlds(HelloWorld helloWorld) {
        helloWorld.setChina("111");
        helloWorld.setUSA("222");
        List<HelloWorld> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add(helloWorld);
        }
        return list;
    }

    /**
     * 实现@Scheduled注解
     * cron 元素可以是一个值（6），一个连续区间（9-12），一个间隔时间（8-18/4 表示每隔4小时）
     * 一个列表（1,2,3），通配符。
     */
    //@Scheduled(fixedDelay = 5000)
    @Scheduled(cron = "0 * * * * ?")
    public void schedule() {
        DateTime nowTime = new DateTime();
        System.out.println("定时任务: "+ nowTime);
    }

}
