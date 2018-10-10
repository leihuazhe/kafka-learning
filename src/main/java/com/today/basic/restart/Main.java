package com.today.basic.restart;

import org.apache.kafka.common.KafkaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class Main {
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
    private RestartableProducer producer;
    private String transId;
    private static AtomicLong atomicLong = new AtomicLong(0);

    public static void main(String[] args) throws IOException, InterruptedException {
        Main main = new Main();
        main.init();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            String input = in.readLine();
            if (input.equals("quit")) {
                System.exit(0);
            }
            if (!"".equals(input)) {
                main.send(atomicLong.incrementAndGet(), input);
            }
        } while (true);


    }


    public void init() {
        producer = new RestartableProducer();
        transId = UUID.randomUUID().toString();
        producer.initTransProducer(transId);
    }


    public void send(Long key, String message) throws InterruptedException {
        try {
            producer.sendMessage("struy", key, Collections.singletonList("struy(" + message + ")"));

        } catch (KafkaException e) {
            LOGGER.warn("关掉了producer ==> {}", producer);
            producer.closeTransProducer();
        } catch (NullPointerException e) {
            LOGGER.warn("producer为空，准备重新启动，重新启动 producer ==> {}", producer);
            producer.initTransProducer(transId);
        } catch (Exception e) {
            LOGGER.error("其他异常: " + e.getMessage(), e);
        }
    }


    /*public static void main(String[] args) throws InterruptedException {
        RestartableProducer producer = new RestartableProducer();

        String transId = UUID.randomUUID().toString();

        producer.initTransProducer(transId);

        for (long i = 0; i < 20; i++) {
            producer.sendMessage("struy", i, Collections.singletonList("struy(" + i + ")"));
        }

        producer.closeTransProducer();

        LOGGER.warn("关掉了producer ==> {}", producer);

        producer.initTransProducer(transId);

        LOGGER.warn("重新启动 producer ==> {}", producer);

        for (long i = 0; i < 20; i++) {
            producer.sendMessage("struy", i, Collections.singletonList("struy(" + i + ")"));
        }

        countDownLatch.await();
    }*/
}
