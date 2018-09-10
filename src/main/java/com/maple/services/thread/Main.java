package com.maple.services.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * desc: TODO
 *
 * @author hz.lei
 * @since 2018年08月03日 下午11:39
 */
public class Main {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        ConcurrentLinkedQueue<Expresses> queue = new ConcurrentLinkedQueue<>();

        for (int i = 1; i <= 100000; i++) {
            queue.add(new Expresses(i, "编号" + i));
        }

        Mailman mailman1 = new Mailman(queue);
        Mailman mailman2 = new Mailman(queue);
        Mailman mailman3 = new Mailman(queue);

        long begin = System.currentTimeMillis();

        executorService.execute(mailman1);
        executorService.execute(mailman2);
        executorService.execute(mailman3);


        executorService.shutdown();

        executorService.awaitTermination(1, TimeUnit.HOURS);

        System.out.println("耗时: " + (System.currentTimeMillis() - begin) + " ms");


        System.out.println("milman1 快递员派发快递: " + mailman1.getCounter());
        System.out.println("milman2 快递员派发快递: " + mailman2.getCounter());
        System.out.println("milman3 快递员派发快递: " + mailman3.getCounter());
    }
}
