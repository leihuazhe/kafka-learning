package com.maple.services.thread;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * desc: Mailman
 *
 * @author hz.lei
 * @since 2018年08月03日 下午11:30
 */
public class Mailman implements Runnable {
    private ConcurrentLinkedQueue<Expresses> expresses;

    private volatile int counter = 0;

    public Mailman(ConcurrentLinkedQueue<Expresses> expresses) {
        this.expresses = expresses;
    }

    @Override
    public void run() {
//        while (expresses.size() > 0) {
        while (true) {
            Expresses exp = this.expresses.poll();
            if (exp == null) {
                break;
            }
//            System.out.println(Thread.currentThread().getName() + " 派发了快递:,id:" + exp.getId() + " name: " + exp.getName());
            counter++;
        }


    }

    public int getCounter() {
        return counter;
    }
}
