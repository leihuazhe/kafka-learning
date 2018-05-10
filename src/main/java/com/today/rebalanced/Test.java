package com.today.rebalanced;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 描述: com.today.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月04日 下午12:59
 */
public class Test {

    public static void main(String[] args) {
        ExecutorService service = Executors.newFixedThreadPool(3);
        List<ReConsumer> reConsumers = new ArrayList<>();
        reConsumers.add(new ReConsumer("rebalancedA", "消费者A"));
        reConsumers.add(new ReConsumer("rebalancedB", "消费者B"));
        reConsumers.add(new ReConsumer("rebalancedC", "消费者C"));

        for (ReConsumer consumer : reConsumers) {
            service.execute(() -> {
                consumer.start();
            });
        }
        System.out.println("start");
    }
}
