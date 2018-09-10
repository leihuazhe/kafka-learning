//package com.today.basic.base;
//
//import com.today.api.scala.event.serializer.MemberBlackedEventSerializer;
//import com.today.api.scala.event.serializer.MemberRegisteredEventSerializer;
//import com.today.eventbus.utils.MsgHandleUtil;
//import org.apache.kafka.clients.consumer.*;
//import org.apache.kafka.common.TopicPartition;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//import java.util.Collection;
//import java.util.Properties;
//
///**
// * @Desc: KafkaConsumerNew
// * @author: maple
// * @Date: 2018-01-29 9:56
// */
//public class DapengMsgConsumer {
//    private static Logger logger = LoggerFactory.getLogger(DapengMsgConsumer.class);
//
//    private static void register() {
//        MsgHandleUtil.register(" com.today.api.scala.event.MemberRegisteredEvent", new MemberRegisteredEventSerializer());
//        MsgHandleUtil.register(" com.today.api.scala.event.MemberBlackedEvent", new MemberBlackedEventSerializer());
//    }
//
//    public static void main(String[] args) {
//        //        KafkaConsumer
//        Properties props = new Properties();
//
//        props.put("bootstrap.servers", "10.10.10.38:9092");
//        props.put("group.id", "maple1");
//        props.put("enable.auto.commit", "false");
//        props.put("auto.commit.interval.ms", "1000");
//
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.LongDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.ByteArrayDeserializer");
//
//        props.put(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed");
//
//        //设置如何把byte转成object类型，例子中，通过指定string解析器，我们告诉获取到的消息的key和value只是简单个string类型。
//        final KafkaConsumer<Long, byte[]> consumer = new KafkaConsumer<>(props);
//
//        consumer.subscribe(Arrays.asList("event"), new ConsumerRebalanceListener() {
//
//            @Override
//            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
//
//            }
//
//            @Override
//            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
////                consumer.seekToBeginning(partitions);
//            }
//        });
//
//        logger.info("start ...");
//
//        while (true) {
//            ConsumerRecords<Long, byte[]> records = consumer.poll(100);
//            for (ConsumerRecord<Long, byte[]> record : records) {
//                System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
//
//                try {
//                    MsgHandleUtil.KafkaMsgInfo msgInfo = MsgHandleUtil.decodeMsg(record.value());
//
//                    System.out.println("msgInfo-eventType: " + msgInfo.getEventType());
//                    System.out.println("msgInfo-event: " + msgInfo.getEvent());
//                } catch (Exception e) {
//                    logger.error(e.getMessage(), e);
//                }
//            }
//        }
//
//    }
//}
