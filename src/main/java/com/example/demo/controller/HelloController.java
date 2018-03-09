package com.example.demo.controller;

import com.example.demo.concurrent.RealData;
import com.example.demo.mqtt.ClientMQTT;
import com.example.demo.mqtt.ServerMQTT;
import com.example.demo.service.HelloService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/hello")
public class HelloController {
    @Autowired
    private HelloService helloService;

    @Value("${number.url}")
    private String url;

    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping
    public void hello() {
        // 测试多环境配置文件
        logger.info("application-dev.yml : {}", url);

        // 测试全局异常捕捉
        //int i = 1 / 0;
    }

    /**
     * 派单问题逻辑实现
     */
    @GetMapping(value = "/dispatch")
    public void dispatch() {
        List<String> driverlist = Arrays.asList("driver1", "driver2", "driver3", "driver4");
        List<String> requestlist = new ArrayList<>();
        List<String> triplist = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            requestlist.add("request" + i);
        }
        int size = driverlist.size();
        for (int i = 0; i < size; i++) {
            for (int j = i; j < requestlist.size(); j += driverlist.size()) {
                triplist.add("trip" + j);
                logger.info("triplist add " + j);
            }

        }

    }

    @GetMapping(value = "/sub")
    public void mqttSub() {
        ClientMQTT client = new ClientMQTT();
        client.setClientId("client11");
        client.start();

    }

    @GetMapping(value = "/pub")
    public void mqttPub() {

        ServerMQTT server = null;
        try {
            server = new ServerMQTT();
        } catch (MqttException e) {
            e.printStackTrace();
        }
        server.mqttMessage = new MqttMessage();
        server.mqttMessage.setQos(1);
        server.mqttMessage.setRetained(false);
        server.mqttMessage.setPayload("hello,topic122".getBytes());
        try {
            server.pulish(server.getTopic11(), server.getMqttMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
        System.out.println(server.mqttMessage.isRetained() + "------ratained状态");

    }

    /**
     * 实验Future模式，并发编程
     */
    @GetMapping(value = "/concurrent")
    public void concurrent() throws InterruptedException, ExecutionException {
        Long start = System.currentTimeMillis();

        FutureTask<String> futureTask = new FutureTask<String>(new RealData("hello,world"));
        ExecutorService executorService = Executors.newFixedThreadPool(1);
        executorService.submit(futureTask);

        // 表示正在处理其他任务
        TimeUnit.SECONDS.sleep(2);
        // get()方法：如果call方法执行完成则给出结果；如果未完成则将当前线程挂起等待
        System.out.println("最后结果-->" + futureTask.get());

        Long end = System.currentTimeMillis();

        Long useTime = end - start;

        System.out.println("程序运行了-->" + useTime + "毫秒");

    }

    /**
     * 体验java8的lambda表达式和stream流api
     */
    @GetMapping(value = "/stream")
    public void stream() {
        // java 8之后
        List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.stream().forEach(n -> System.out.println(n));
        // 等价于
        features.forEach(System.out::println);

        // Predicate函数式接口的api
        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        // lambda表达式不能对定义在域外的变量进行修改
        List<Integer> primes = Arrays.asList(new Integer[]{2, 3, 5, 7});
        int factor = 1;
        primes.forEach(e -> {
            System.out.println(factor);
        });

    }


}


