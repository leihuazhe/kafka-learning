package com.today.basic.stress;

import com.today.basic.util.KafkaUtils;
import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Random;

/**
 * @Desc: KafkaBase kafka 客户端发送 record (消息) 到 kafka 集群
 * @author: maple
 * @Date: 2018-01-18 15:44
 */
public class KafkaStressProducer {

    private static Logger logger = LoggerFactory.getLogger(KafkaStressProducer.class);

    /**
     * 生产者的缓冲空间池保留尚未发送到服务器的消息
     *
     * @param args
     */
    public static void main(String[] args) {
        Properties props = KafkaUtils.buildProperties();
        Producer<String, String> producer = new KafkaProducer<>(props);

        try {
            while (true) {
                /**
                 * send 是异步的， 添加到缓冲区后，马上返回。生产者将单个消息批量来进行发送 来提高效率
                 */
                final String TOPIC = "member_test";
                producer.send(new ProducerRecord<>(TOPIC, "member_test", "member_test"), (Callback) (metadata, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        int partition = metadata.partition();
                        long offset = metadata.offset();
                        logger.info("已经发送成功 offset:{},partition:{}", offset, partition);
                    }
                });
                Random random = new Random();
                int res = random.nextInt(10);
                Thread.sleep(res * 100);
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

    }
}
