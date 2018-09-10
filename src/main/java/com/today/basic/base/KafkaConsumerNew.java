package com.today.basic.base;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

/**
 * @Desc: KafkaConsumerNew
 * @author: maple
 * @Date: 2018-01-29 9:56
 */
public class KafkaConsumerNew {
    private static Logger logger = LoggerFactory.getLogger(KafkaConsumerNew.class);


    public static void main(String[] args) {
        //        KafkaConsumer
        Properties props = new Properties();
        //消费者开启即可
//        props.put(ConsumerConfig.EXCLUDE_INTERNAL_TOPICS_CONFIG, false);

        props.put("bootstrap.servers", "127.0.0.1:9092");
        props.put("group.id", "maple");
        props.put("enable.auto.commit", "false");
        props.put("auto.commit.interval.ms", "1000");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");

        //设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
        final KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

       /* consumer.subscribe(Arrays.asList("struy"), new ConsumerRebalanceListener() {

            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                logger.info("[RebalanceListener-Revoked]:reblance触发, partition被收回");
                partitions.forEach(p -> {
                    long position = consumer.position(p);
                    logger.info("partition:{}, next offset:{} ", p, position);
                    OffsetAndMetadata committed = consumer.committed(p);
                    logger.info("OffsetAndMetadata: {}", committed);
                });
                consumer.commitSync();
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
//                consumer.seekToBeginning(partitions);
//                partitions.forEach(p -> consumer.seek(p,211));
                logger.info("[RebalanceListener-Assigned]:reblance 触发, partition重新分配");
                partitions.forEach(partition -> {
                    //获取消费偏移量，实现原理是向协调者发送获取请求
                    OffsetAndMetadata offset = consumer.committed(partition);
                    logger.info("onPartitionsAssigned: partition:{}, offset:{}", partition, offset);
                    if (offset == null) {
                        logger.info("assigned offset is null ,do nothing for it !");
                    } else {
                        //设置本地拉取分量，下次拉取消息以这个偏移量为准
                        consumer.seek(partition, offset.offset());
                    }
                });
            }
        });*/

        logger.info("start ...");

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(100);
//            System.out.println("poll size:" + records.count());

            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("offset = %d, key = %s, value = %s ,spartition = %d \n", record.offset(), record.key(), record.value(), record.partition());
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                consumer.commitAsync();


                TopicPartition tp = new TopicPartition(record.topic(), record.partition());
//                consumer.seek(tp,211);
//                break;

            }


            logger.debug("what?");


        }

    }
}
