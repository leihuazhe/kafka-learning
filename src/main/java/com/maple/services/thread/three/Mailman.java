package com.maple.services.thread.three;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Mailman implements Runnable {
    private volatile int count = 0;
    //1.改为 类 持有  ，这样三个对象的锁相同
    //	private final Lock lock =new ReentrantLock();
    private static final Lock lock = new ReentrantLock();

    private static int num = Expresses.getTotalExpresses();

    private CountDownLatch latch;

    public void run() {
      /*  int num1 = num;
        for (int i = 0; i < num1; i++) {
            dispatch();
        }*/
        //2.使用  while  true
        dispatch();
    }

    public Mailman(CountDownLatch latch) {
        this.latch = latch;
    }

    public void dispatch() {

        while (true) {
            //上锁
            lock.lock();
            try {
                if (num > 0) {
                    System.out.println(Thread.currentThread().getName() + "派送了" + num-- + "号快递");
//                    Thread.sleep(10);
                    count++;
                } else {
                    //3.使用 latch ，当size为0 后 退出循环
                    latch.countDown();
                    break;
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                //开锁
                lock.unlock();
            }
        }

    }

    public int getCount() {
        return count;
    }
}
