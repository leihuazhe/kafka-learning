package com.today.basic.restart;

import org.apache.kafka.clients.producer.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Properties;
import java.util.Random;

/**
 * RestartableProducer
 */
public class RestartableProducer {
    private static final Logger LOGGER = LoggerFactory.getLogger(RestartableProducer.class);

    private KafkaProducer<String, String> producer;

    public void initTransProducer(String transactionId) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("acks", "all");
        props.put("retries", 1);
        props.put("batch.size", 16384); //缓存每个分区未发送消息
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, transactionId);
        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, "true");

        producer = new KafkaProducer<>(props);
        producer.initTransactions();
    }

    public void closeTransProducer() {
        producer.close();
        producer = null;
    }


    public void sendMessage(String topic, Long id, List<String> message) {
        try {
            producer.beginTransaction();

            message.forEach(msg -> {
                producer.send(new ProducerRecord<>(topic, Long.toString(id), msg), (metadata, ex) -> {
                    if (ex != null) {
                        LOGGER.error(ex.getMessage(), ex);
                    } else {
                        int partition = metadata.partition();
                        long offset = metadata.offset();
                        LOGGER.info("已经发送成功 offset: {}, partition: {}   ", offset, partition);
                    }
                });
            });
            Random random = new Random();
            int res = random.nextInt(10);

            producer.commitTransaction();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            producer.abortTransaction();
            throw e;
        }


        /*
            send()方法是异步的，添加消息到缓冲区等待发送，并立即返回。生产者将单个的消息批量在一起发送来提高效率。

            ack是判别请求是否为完整的条件（就是是判断是不是成功发送了）。我们指定了“all”将会阻塞消息，这种设置性能最低，但是是最可靠的。

            retries，如果请求失败，生产者会自动重试，我们指定是0次，如果启用重试，则会有重复消息的可能性。

            producer(生产者)缓存每个分区未发送消息。缓存的大小是通过 batch.size 配置指定的。值较大的话将会产生更大的批。并需要更多的内存（因为每个“活跃”的分区都有1个缓冲区）。

            默认缓冲可立即发送，即遍缓冲空间还没有满，但是，如果你想减少请求的数量，可以设置linger.ms大于0。这将指示生产者发送请求之前等待一段时间，希望更多的消息填补到未满的批中。这类似于TCP的算法，例如上面的代码段，可能100条消息在一个请求发送，因为我们设置了linger(逗留)时间为1毫秒，然后，如果我们没有填满缓冲区，这个设置将增加1毫秒的延迟请求以等待更多的消息。需要注意的是，在高负载下，相近的时间一般也会组成批，即使是 linger.ms=0。在不处于高负载的情况下，如果设置比0大，以少量的延迟代价换取更少的，更有效的请求。

            buffer.memory 控制生产者可用的缓存总量，如果消息发送速度比其传输到服务器的快，将会耗尽这个缓存空间。当缓存空间耗尽，其他发送调用将被阻塞，阻塞时间的阈值通过max.block.ms设定，之后它将抛出一个TimeoutException。

            key.serializer和value.serializer示例，将用户提供的key和value对象ProducerRecord转换成字节，你可以使用附带的ByteArraySerializaer或StringSerializer处理简单的string或byte类型。
         */


    }
}
