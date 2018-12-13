package com.maple.services;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * @Desc: BusinessProducer kafka 客户端发送 record (消息) 到 kafka 集群
 * @author: maple
 * @Date: 2018-01-18 15:44
 */
public class BusinessProducer {
    private static Logger logger = LoggerFactory.getLogger(BusinessProducer.class);

    /**
     * 生产者的缓冲空间池保留尚未发送到服务器的消息
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", LongDeserializer.class);
        props.put("value.serializer", ByteArrayDeserializer.class);

        Producer<Long, byte[]> producer = new KafkaProducer<>(props);

        try {
            final String topic = "struy3";

            EventStore eventStore = buildEventMsg();

            producer.send(new ProducerRecord<>(topic, eventStore.getId(), eventStore.getEventBinary()), (metadata, exception) -> {
                if (exception != null) {
                    logger.error(exception.getMessage(), exception);
                } else {

                    int partition = metadata.partition();
                    long offset = metadata.offset();

                    System.out.println("已经发送成功 offset:   " + offset + " , p:" + partition);
                }
            });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public static EventStore buildEventMsg() {
        return new EventStore();
    }


}
