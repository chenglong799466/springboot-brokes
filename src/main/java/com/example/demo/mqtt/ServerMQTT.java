package com.example.demo.mqtt;

import lombok.Data;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Data
public class ServerMQTT {
    // tcp://MQTT安装的服务器地址：MQTT定义的端口号
    public static final String HOST = "tcp://192.168.20.17:1883";
    // 定义一个主题
    public static final String TOPIC = "test";
    // 定义MQTT的ID，可以在MQTT服务配置中指定
    private static final String clientId = "server11";

    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "mosquitto";
    private String passWord = "mosquitto";

    public MqttMessage mqttMessage;

    /**
     * 构造函数
     *
     * @throws MqttException
     */
    public ServerMQTT() throws MqttException {
        // MemoryPersistence设置clientid的保存形式，默认为以内存保存
        client = new MqttClient(HOST, clientId, new MemoryPersistence());
        connect();
    }

    private void connect(){
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback());
            client.connect(options);
            topic11 = client.getTopic(TOPIC);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (MqttSecurityException e) {
            e.printStackTrace();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public void pulish(MqttTopic topic,MqttMessage message) throws MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! " + token.isComplete());

    }
}
