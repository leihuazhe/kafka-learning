package com.today.eventbus.consumer;

import com.github.dapeng.core.SoaException;
import com.github.dapeng.org.apache.thrift.TException;
import com.today.eventbus.util.ConsumerEndpoint;
import com.today.eventbus.util.KafkaConfigBuilder;
import com.today.eventbus.util.MsgConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.CommitFailedException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.*;

/**
 * desc: MsgConsumer
 *
 * @author hz.lei
 * @since 2018年08月09日 下午9:02
 */
public class MsgConsumer implements Runnable {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected ConsumerEndpoint bizConsumer;
    protected String groupId;

    protected String topic;

    protected String kafkaConnect;

    protected KafkaConsumer<String, String> consumer;

    private volatile boolean isRunning;


    public MsgConsumer(String kafkaHost, String groupId, String topic, ConsumerEndpoint consumerEndpoint) {
        this.kafkaConnect = kafkaHost;
        this.groupId = groupId;
        this.topic = topic;
        this.bizConsumer = consumerEndpoint;
        init();
        isRunning = true;
    }


    public void init() {
        KafkaConfigBuilder.ConsumerConfiguration builder = KafkaConfigBuilder.defaultConsumer();

        final Properties props = builder.bootstrapServers(kafkaConnect)
                .group(groupId)
                .withKeyDeserializer(StringDeserializer.class)
                .withValueDeserializer(StringDeserializer.class)
                .withOffsetCommitted(false)
                .maxPollSize(50)
                .build();

        consumer = new KafkaConsumer<>(props);
    }

    /**
     * 添加相同的 group + topic  消费者
     *
     * @param endpoint
     */
    public void addConsumer(ConsumerEndpoint endpoint) {
        this.bizConsumer = endpoint;
    }


    public void stopRunning() {
        isRunning = false;
        logger.info(getClass().getSimpleName() + "::stop the kafka consumer to fetch message ");
    }


    @Override
    public void run() {
        logger.info("[" + getClass().getSimpleName() + "][ {} ][run] ", this.groupId + ":" + this.topic);
        //增加partition平衡监听器回调
        this.consumer.subscribe(Collections.singletonList(this.topic), new MsgConsumerRebalanceListener(consumer, topic));

        while (isRunning) {
            try {
                ConsumerRecords<String, String> records = consumer.poll(100);
                if (records != null && records.count() > 0) {
                    logger.info("[" + getClass().getSimpleName() + "] 每轮拉取消息数量,poll received : " + records.count() + " records");
                    // for process every message
                    for (ConsumerRecord<String, String> record : records) {
                        logger.info("[" + getClass().getSimpleName() + "] receive message (收到消息，准备过滤，然后处理), topic: {} ,partition: {} ,offset: {}",
                                record.topic(), record.partition(), record.offset());
                        try {
                            dealMessage(bizConsumer, record.value());
                        } catch (Exception e) {
                            logger.error(getClass().getSimpleName() + "::[订阅消息处理失败]: " + e.getMessage(), e);
                        }
                    }
                    try {
                        //records记录全部完成后，才提交
                        consumer.commitSync();
                    } catch (CommitFailedException e) {
                        logger.error("commit failed", e);
                    }
                }

            } catch (SerializationException ex) {
                logger.error("kafka consumer poll 反序列化消息异常:" + ex.getMessage(), ex);
            } catch (Exception e) {
                logger.error("[KafkaConsumer][{}][run] " + e.getMessage(), groupId + ":" + topic, e);
            }
        }
        consumer.close();
        logger.info("[{}]::kafka consumer stop running already!", getClass().getSimpleName());
    }

    /**
     * 1. 反射调用的 目标类如果抛出异常 ，将被包装为 InvocationTargetException e
     * 2. 通过  InvocationTargetException.getTargetException 可以得到目标具体抛出的异常
     * 3. 如果目标类是通过aop代理的类,此时获得的异常会是 UndeclaredThrowableException
     * 4.如果目标类不是代理类，获得异常将直接为原始目标方法抛出的异常
     * <p>
     * 因此,需要判断目标异常如果为UndeclaredThrowableException，需要再次 getCause 拿到原始异常
     */
    protected void throwRealException(InvocationTargetException e, String methodName) throws SoaException {
        Throwable target = e.getTargetException();

        if (target instanceof UndeclaredThrowableException) {
            target = target.getCause();
        }
        logger.error("[" + getClass().getSimpleName() + "]::[TargetException]:" + target.getClass(), target.getMessage());

        if (target instanceof SoaException) {
            logger.error("[" + getClass().getSimpleName() + "]::[订阅者处理消息失败,不会重试] throws SoaException: " + target.getMessage(), target);
            return;
        }
        throw new SoaException("deal message failed, throws: " + target.getMessage(), methodName);
    }


    /**
     * 消息具体处理逻辑
     *
     * @param bizConsumer 多个业务消费者遍历执行
     * @param value
     * @throws TException SoaException 是其子类 受检异常
     */
    protected void dealMessage(ConsumerEndpoint bizConsumer, String value) throws InterruptedException {
        logger.info("处理消息: " + bizConsumer.toString() + ", 内容:" + value);
        if ("测试".equals(value)) {
            logger.info("its 测试");
            Thread.sleep(320000);
            logger.info("处理测试完毕");
        }
        Thread.sleep(800);
    }
}
