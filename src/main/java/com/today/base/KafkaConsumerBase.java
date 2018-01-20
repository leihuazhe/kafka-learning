package com.today.base;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Test;

import java.util.Arrays;
import java.util.Properties;

/**
 * @Desc: KafkaConsumerBase
 * @author: maple
 * @Date: 2018-01-18 20:37
 */
public class KafkaConsumerBase {
    /**
     *


     Kafka客户端从集群中消费消息
     *
     *
     *
     *
     *
     * @param args
     */
    public static void main(String[] args) {
    //        KafkaConsumer
        Properties props = new Properties();
        props.put("bootstrap.servers", "123.206.103.113:9092");
        //消费者组ID
        props.put("group.id", "consumer-test");
        props.put("enable.auto.commit", "true");
        //控制自动提交的频率。
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }


    }

    /**
     *  一个消费组里的所有消费者，只有一个能够去消费一个分区，除非这个down了，
     *  其他的消费者才能去消费
     *
     *
     *
     */
    @Test
    public  void test () {
        //        KafkaConsumer
        Properties props = new Properties();
        props.put("bootstrap.servers", "123.206.103.113:9092");
        //消费者组ID
        props.put("group.id", "consumer-test1");
        props.put("enable.auto.commit", "true");
        //控制自动提交的频率。
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }


    }

}
