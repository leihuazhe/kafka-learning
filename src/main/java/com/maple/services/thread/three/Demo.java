package com.maple.services.thread.three;

import java.util.concurrent.CountDownLatch;

public class Demo {

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(3);

        Mailman mailman1 = new Mailman(latch);
        Mailman mailman2 = new Mailman(latch);
        Mailman mailman3 = new Mailman(latch);


        Thread m1 = new Thread(mailman1, "小明");
        Thread m2 = new Thread(mailman2, "小红");
        Thread m3 = new Thread(mailman3, "小白");
        m1.start();
        m2.start();
        m3.start();
        //等待释放，3把锁。 latch.countDown
        latch.await();

        System.out.println(m1.getName() + "共派送了" + mailman1.getCount());
        System.out.println(m2.getName() + "共派送了" + mailman2.getCount());
        System.out.println(m3.getName() + "共派送了" + mailman3.getCount());
    }

}
