package com.today.partition;

import org.apache.kafka.clients.producer.*;

import java.util.Properties;
import java.util.Random;
import java.util.concurrent.Future;

/**
 * Desc: KafkaParProducer 带有分区的
 * author: maple
 * Date: 2018-01-20 14:19
 */
public class KafkaParProducer {

    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "123.206.103.113:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("batch.size", 16384); //缓存每个分区未发送消息
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("partitioner.class", "com.today.partition.SimplePartitioner");


        Producer<String, String> producer = new KafkaProducer<>(props);

        for (int i = 0; i < 100; i++) {
            /**
             * send 是异步的， 添加到缓冲区后，马上返回。生产者将单个消息批量来进行发送 来提高效率
             */
            Future<RecordMetadata> recordMetadata =
                    producer.send(new ProducerRecord<>("test", Integer.toString(i), "exception" + Integer.toString(i)), new Callback() {
                        @Override
                        public void onCompletion(RecordMetadata metadata, Exception exception) {
                            if (exception != null) {
                                exception.printStackTrace();
                            }
                            System.out.println("已经发送成功  " + metadata.toString());
                            //                    RecordMetadata
                        }
                    });

            Random random = new Random();
            int res = random.nextInt(10);
            /*try{
                if(res <= 1) {throw  new RuntimeException("故意异常"); }

            }catch (Exception e) {
                e.printStackTrace();
            }*/

//            recordMetadata.
        }
    }
}
