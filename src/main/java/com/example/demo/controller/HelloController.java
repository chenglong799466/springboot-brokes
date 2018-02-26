package com.example.demo.controller;

import com.example.demo.concurrent.RealData;
import com.example.demo.model.HelloWorld;
import com.example.demo.mqtt.ClientMQTT;
import com.example.demo.mqtt.ServerMQTT;
import com.example.demo.service.HelloService;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.plugin.javascript.navig.Array;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private HelloWorld helloWorld;

    @Autowired
    private HelloService helloService;

    @GetMapping(value = "/1")
    public HelloWorld hello() {
        List<HelloWorld> list = helloService.helloWorlds(helloWorld);
        for (HelloWorld h : list) {
            return h;
        }
        return null;
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
     * 实验Future模式
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
        List features = Arrays.asList("Lambdas","Default Method","Stream API","Date and Time API");
        features.stream().forEach(n -> System.out.println(n));
        // 等价于
        features.forEach(System.out::println);

        // Predicate函数式接口的api
        List languages = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");

        // lambda表达式不能对定义在域外的变量进行修改
        List<Integer> primes = Arrays.asList(new Integer[]{2, 3,5,7});
        int factor = 1;
        primes.forEach(e ->{
            System.out.println(factor);
        });

    }


}


