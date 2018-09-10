package com.today.eventbus.producer;

import com.today.eventbus.util.KafkaConfigBuilder;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * desc: ProducerBean
 *
 * @author hz.lei
 * @since 2018年08月09日 下午8:37
 */
public class ProducerBean {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private KafkaProducer<String, String> producer;

    public void init() {
        KafkaConfigBuilder.ProducerConfiguration builder = KafkaConfigBuilder.defaultProducer();

        Properties properties = builder.withKeySerializer(StringSerializer.class)
                .withValueSerializer(StringSerializer.class)
                .bootstrapServers("127.0.0.1:9092")
                .build();
        producer = new KafkaProducer<>(properties);
    }

    public void sendMessage(String topic, String id, String msg) {
        producer.send(new ProducerRecord<>(topic, id, msg), (RecordMetadata metadata, Exception ex) -> {
            if (ex != null) {
                if (metadata != null) {
                    logger.error("send message to broker failed,id:{}, topic:{}, offset:{}, partition: {}", id, topic, metadata.offset(), metadata.partition());
                }
                logger.error("send message to broker failed,id:{}, topic:{}", id, topic);

            } else {
                logger.info("发送消息成功,id:{}, topic:{}, offset:{}, partition:{}", id, topic, metadata.offset(), metadata.partition());
            }
        });
    }

}
