package com.today.rebalanced;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sound.midi.Soundbank;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

/**
 * 描述: com.today.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月04日 下午12:54
 */
public class ReConsumer {

    private static Logger logger = LoggerFactory.getLogger(ReConsumer.class);
    private KafkaConsumer<String, String> consumer;
    private String consumerId;
    private String mark;

    public ReConsumer(String consumerId, String mark) {
        this.consumerId = consumerId;
        this.mark = mark;
    }

    public void init() {
        Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, consumerId);
        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "maple");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        consumer = new KafkaConsumer<>(props);

        consumer.subscribe(Arrays.asList("rebalanced"), new ConsumerRebalanceListener() {

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                partitions.forEach(p -> {
                    long position = consumer.position(p);
                    System.out.println("next offset:" + position);
                    OffsetAndMetadata committed = consumer.committed(p);

                    System.out.println("partitions: " + p);

                    System.out.println("onPartitionsRevoked: " + committed);

                });
                consumer.commitSync();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
                partitions.forEach(partition -> {
                    //获取消费偏移量，实现原理是向协调者发送获取请求
                    OffsetAndMetadata offset = consumer.committed(partition);
                    System.out.println("onPartitionsAssigned: " + offset);
                    //设置本地拉取分量，下次拉取消息以这个偏移量为准
                    consumer.seek(partition, offset.offset());
                });
            }
        });

        logger.info("start ...");
    }

    public void start() {
        init();
        while (true) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ConsumerRecords<String, String> records = consumer.poll(100);
            int i = 0;
            for (ConsumerRecord<String, String> record : records) {
                i++;
                System.out.println("偏移量i: " + i);
                System.out.println("records: " + records.count());
                if (record.offset() % 10 == 0) {
                    System.out.println("0结尾，开始sleep! record:" + record.offset());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("<=> offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            System.out.println("ready to commit ");
//            consumer.commitSync();
            /*consumer.commitAsync(new OffsetCommitCallback() {
                @Override
                public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                    if (exception == null) {
                        System.out.println("提交偏移量成功! offsets: " + offsets);
                    } else {
                        System.out.println("提交偏移量失败: " + exception.getMessage());
                    }
                }
            });*/
        }
    }

    public static void main(String[] args) {
        ReConsumer reConsumer = new ReConsumer("rebalancedA", "消费者A");
    }
}
