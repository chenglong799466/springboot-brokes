package com.example.demo;

import com.example.demo.mqtt.ClientMQTT;
import com.example.demo.mqtt.ServerMQTT;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    @Test
    public void contextLoads() {
        System.out.println("===================================");
    }

    @Test
    public void testMqtt() throws MqttException {
        ServerMQTT server = new ServerMQTT();

        ClientMQTT client = new ClientMQTT();
        client.start();

        server.mqttMessage = new MqttMessage();
        server.mqttMessage.setQos(1);
        server.mqttMessage.setRetained(true);
        server.mqttMessage.setPayload("hello,topic122".getBytes());
        server.pulish(server.getTopic11(), server.getMqttMessage());
        System.out.println(server.mqttMessage.isRetained() + "------ratained状态");

    }

}
