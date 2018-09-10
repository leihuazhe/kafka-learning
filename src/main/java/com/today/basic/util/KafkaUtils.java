package com.today.basic.util;

import java.util.Properties;

/**
 * desc: KafkaUtils
 *
 * @author hz.lei
 * @since 2018年07月25日 下午4:50
 */
public class KafkaUtils {

    public static Properties buildProperties() {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        //缓存每个分区未发送消息
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
}
