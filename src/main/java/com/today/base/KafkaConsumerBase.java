package com.today.base;


import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.junit.Test;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;

import java.util.*;

/**
 * @Desc: KafkaConsumerBase
 * @author: maple
 * @Date: 2018-01-18 20:37
 */
public class KafkaConsumerBase {
    /**
     * Kafka客户端从集群中消费消息
     * <p>
     * broker通过心跳机器自动检测test组中失败的进程，消费者会自动ping集群，告诉进群它还活着。
     * 只要消费者能够做到这一点，它就被认为是活着的，并保留分配给它分区的权利，
     * 如果它停止心跳的时间超过session.timeout.ms,那么就会认为是故障的，
     * 它的分区将被分配到别的进程。
     *
     * @param args
     */
    public static void main(String[] args) {
        //        KafkaConsumer
        Properties props = new Properties();
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("zookeeper.connect", "127.0.0.1:2181");
        //消费者组ID
        props.put("group.id", "consumer");
        props.put("enable.auto.commit", "true");
        //控制自动提交的频率。
        props.put("auto.commit.interval.ms", "1000");
        //deserializer
        //设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG,"read_committed");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test","my-topic", "bar"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
        }


    }

    /**
     * 设置enable.auto.commit,偏移量由auto.commit.interval.ms控制自动提交的频率。
     * <p>
     * 集群是通过配置bootstrap.servers指定一个或多个broker。不用指定全部的broker，它将自动发现集群中的其余的borker（最好指定多个，万一有服务器故障）。
     * <p>
     * 在这个例子中，客户端订阅了主题foo和bar。消费者组叫test。
     * <p>
     * broker通过心跳机器自动检测test组中失败的进程，消费者会自动ping集群，告诉进群它还活着。只要消费者能够做到这一点，它就被认为是活着的，并保留分配给它分区的权利，如果它停止心跳的时间超过session.timeout.ms,那么就会认为是故障的，它的分区将被分配到别的进程。
     * <p>
     * 这个deserializer设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
     */
    @Test
    public void test() {
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


    /**
     * 手动控制偏移量
     */
    @Test
    public void testByHand() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "123.206.103.113:9092");
        props.put("group.id", "test");
        // false
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("session.timeout.ms", "3000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test", "bar"));

        final int minBatchSize = 200;

        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        /**
         *  针对复杂业务逻辑 -- -- --
         在这个例子中，我们将消费一批消息并将它们存储在内存中。
         当我们积累足够多的消息后，我们再将它们批量插入到数据库中。
         如果我们设置offset自动提交（之前说的例子），消费将被认为是已消费的。
         这样会出现问题，我们的进程可能在批处理记录之后，但在它们被插入到数据库之前失败了。
         */
        /*while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            if (buffer.size() >= minBatchSize) {
//                insertIntoDb(buffer);
                consumer.commitSync();
                buffer.clear();
            }
        }*/


        /**
         *
         * 在某些情况下，你可以希望更精细的控制，
         * 通过指定一个明确消息的偏移量为“已提交”。
         * 在下面，我们的例子中，我们处理完每个分区中的消息后，提交偏移量。
         *
         */
        try {
            while(true) {
                ConsumerRecords<String, String> records = consumer.poll(Long.MAX_VALUE);
                for (TopicPartition partition : records.partitions()) {

                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);

                    for (ConsumerRecord<String, String> record : partitionRecords) {
                        System.out.println(record.offset() + ": " + record.value());
                    }
                    long lastOffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastOffset + 1)));
                }
            }
        } finally {
            consumer.close();
        }
    }

}
