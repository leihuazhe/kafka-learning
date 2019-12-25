package com.yunji.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * @Desc: KafkaBase kafka 客户端发送 record (消息) 到 kafka 集群
 * @author: maple
 * @Date: 2018-01-18 15:44
 */
public class KafkaProducer_24 {

    public static final Properties PROPERTIES = new Properties();

    public static final String TOPIC = "feature";

    public static Producer<String, String> producer;


    static {
        PROPERTIES.put("bootstrap.servers", "localhost:9092");
        PROPERTIES.put("acks", "all");
        PROPERTIES.put("retries", 1);
        PROPERTIES.put("batch.size", 16384); //缓存每个分区未发送消息
        PROPERTIES.put("linger.ms", 1);
        PROPERTIES.put("buffer.memory", 33554432);
        PROPERTIES.put("key.serializer", StringSerializer.class);
        PROPERTIES.put("value.serializer", StringSerializer.class);
        producer = new KafkaProducer<>(PROPERTIES);
    }

    public static void main(String[] args) {
        sendTest();
    }


    /**
     * 生产者的缓冲空间池保留尚未发送到服务器的消息
     */
    public static void sendTest() {

        try {
            for (int i = 0; i < 100000; i++) {
                producer.send(new ProducerRecord<>(TOPIC, Integer.toString(i), TOPIC + Integer.toString(i)), (metadata, exception) -> {
                    if (exception != null) {
                        exception.printStackTrace();
                    } else {
                        int partition = metadata.partition();
                        long offset = metadata.offset();
                        System.out.println("Successful: offset: " + offset + ", p: " + partition + ", time: " + System.currentTimeMillis());
                    }
                });
                Thread.sleep(300);
            }
            System.out.println("<====END====>");
//            producer.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            producer.abortTransaction();
        }


    }
}
