package com.today.eventbus;

import com.today.eventbus.consumer.MsgConsumer;
import com.today.eventbus.util.ConsumerEndpoint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * desc: Main
 *
 * @author hz.lei
 * @since 2018年08月09日 下午8:36
 */
public class Main {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        MsgConsumer consumer1 = new MsgConsumer("127.0.0.1:9092", "maple", "rebalance", new ConsumerEndpoint("consumer-1"));
        MsgConsumer consumer2 = new MsgConsumer("127.0.0.1:9092", "maple", "rebalance", new ConsumerEndpoint("consumer-1"));
        executorService.execute(consumer1);
        executorService.execute(consumer2);
    }
}
