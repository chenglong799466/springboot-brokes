package com.example.demo.service;


import com.example.demo.model.World;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {
    @Transactional
    public List<World> helloWorlds() {
        List<World> list = new ArrayList<>();
        return list;
    }

    /**
     * 实现@Scheduled注解
     * cron 元素可以是一个值（6），一个连续区间（9-12），一个间隔时间（8-18/4 表示每隔4小时）
     * 一个列表（1,2,3），通配符。
     */
    // @Scheduled(fixedDelay = 5000)
    //@Scheduled(cron = "0 * * * * ?")
    public void schedule() {
        DateTime nowTime = new DateTime();
        System.out.println("定时任务: "+ nowTime);
    }


}
