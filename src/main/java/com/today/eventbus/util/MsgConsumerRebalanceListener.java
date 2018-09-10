package com.today.eventbus.util;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * desc: MsgConsumerRebalanceListener
 *
 * @author hz.lei
 * @since 2018年07月25日 上午11:48
 */
public class MsgConsumerRebalanceListener implements ConsumerRebalanceListener {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private final Consumer consumer;
    private final String topic;

    private OffsetManager offsetManager = new OffsetManager();

    public MsgConsumerRebalanceListener(Consumer consumer, String topic) {
        this.consumer = consumer;
        this.topic = topic;

    }

    @Override
    public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
        logger.info("[RebalanceListener-Revoked]:reblance触发, partition被收回");
        partitions.forEach(p -> {
            long position = consumer.position(p);
            OffsetAndMetadata committed = consumer.committed(p);

            logger.info("partition:{}, next offset:{} ,OffsetAndMetadata:{}", p, position, committed);

            offsetManager.saveOffsetInExternalStore(topic, p.partition(), position);
        });
    }


    @Override
    public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
        logger.info("[RebalanceListener-Assigned]:reblance 触发, partition重新分配");
        partitions.forEach(partition -> {
            //获取消费偏移量，实现原理是向协调者发送获取请求
            long offset = offsetManager.readOffsetFromExternalStore(topic, partition.partition());
            logger.info("onPartitionsAssigned: partition:{}, offset:{}", partition, offset);
            consumer.seek(partition, offset);
        });
    }
}

