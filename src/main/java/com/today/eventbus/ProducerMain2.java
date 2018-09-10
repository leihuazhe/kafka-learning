package com.today.eventbus;

import com.today.eventbus.producer.ProducerBean;

import java.util.Scanner;


/**
 * desc: Main
 *
 * @author hz.lei
 * @since 2018年08月09日 下午8:36
 */
public class ProducerMain2 {

    public static void main(String[] args) throws InterruptedException {
        ProducerBean producerBean = new ProducerBean();
        producerBean.init();
        for (int i = 0; i < 100; i++) {
//            producerBean.sendMessage("rebalance", i + "", "大佬-" + i);
            producerBean.sendMessage("struy", i + "", "大佬-" + i);
        }

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.next();
            if ("while".equals(input)) {
                for (int i = 0; i < 100; i++) {
                    producerBean.sendMessage("rebalance", i + "", "while-" + i);
                }
            }
            producerBean.sendMessage("rebalance", input, input);
        }


    }
}
