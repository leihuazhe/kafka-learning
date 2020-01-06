package com.yunji.kafka.producer;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.*;
import java.util.concurrent.Future;

import static org.apache.kafka.clients.producer.ProducerConfig.COMPRESSION_TYPE_CONFIG;

/**
 * @Desc: KafkaBase kafka 客户端发送 record (消息) 到 kafka 集群
 * @author: maple
 * @Date: 2018-01-18 15:44
 */
public class KafkaProducer_24 {

    public static final Properties PROPERTIES = new Properties();
    public static final Map<String, Integer> partitionsMap = new HashMap<>();

    public static final String TOPIC = "feature";

    public static Producer<String, String> producer;

    public static void main(String[] args) {
        sendTest();
    }


    static {
        PROPERTIES.put("bootstrap.servers", "localhost:9092");
        PROPERTIES.put("acks", "all");
        PROPERTIES.put("retries", 1);
        PROPERTIES.put("batch.size", 16384); //缓存每个分区未发送消息
        PROPERTIES.put("linger.ms", 500);
        PROPERTIES.put("buffer.memory", 33554432);
        PROPERTIES.put("key.serializer", StringSerializer.class);
        PROPERTIES.put("value.serializer", StringSerializer.class);
        //指定partitioner策略
//        PROPERTIES.put("partitioner.class", RoundRobinPartitioner.class);
        //使用压缩策略.
        PROPERTIES.put(COMPRESSION_TYPE_CONFIG, "zstd");
        producer = new KafkaProducer<>(PROPERTIES);
    }


    /**
     * 生产者的缓冲空间池保留尚未发送到服务器的消息
     */
    public static void sendTest() {
        try {
            for (int i = 0; i < 100000; i++) {
                for (int i1 = 0; i1 < 200; i1++) {
                    producer.send(new ProducerRecord<>(TOPIC, /*Integer.toString(i), */TOPIC + Integer.toString(i)), (metadata, exception) -> {
                        if (exception != null) {
                            exception.printStackTrace();
                        } else {
                            int partition = metadata.partition();
                            long offset = metadata.offset();

                            partitionsMap.compute("partition-" + partition, (k, v) -> {
                                if (v == null) {
                                    return 1;
                                }
                                return v + 1;
                            });
//                            System.out.println("Successful: offset: " + offset + ", p: " + partition + ", time: " + System.currentTimeMillis());
                        }
                    });
                    Thread.sleep(1);
                }
                Thread.sleep(3000);

                StringBuilder sb = new StringBuilder();
                partitionsMap.forEach((k, v) -> {
                    sb.append("\n").append(k).append(", count: ").append(v);
                });

                System.out.println("\n\n\n\n partitionList:" + sb.toString());
                partitionsMap.clear();

                Thread.sleep(2000);
            }
            System.out.println("<====END====>");
//            producer.commitTransaction();
        } catch (Exception e) {
            e.printStackTrace();
            producer.abortTransaction();
        }


    }
}
