package com.maple.services.thread.two;


import com.maple.services.thread.Expresses;

import java.util.List;

/**
 * desc: Mailman
 *
 * @author hz.lei
 * @since 2018年08月03日 下午11:30
 */
public class Mailman implements Runnable {
    private final List<Expresses> expresses;

    private volatile int counter = 0;

    public Mailman(List<Expresses> expresses) {
        this.expresses = expresses;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (expresses) {
                if (expresses.size() <= 0) {
                    break;
                }
                Expresses exp = this.expresses.remove(0);
//                System.out.println(Thread.currentThread().getName() + " 派发了快递:,id:" + exp.getId() + " name: " + exp.getName());
                counter++;
            }
        }
    }

    public int getCounter() {
        return counter;
    }
}
