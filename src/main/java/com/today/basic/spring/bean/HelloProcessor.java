package com.today.basic.spring.bean;

import org.springframework.kafka.annotation.KafkaListener;

/**
 * desc: HelloProcessor
 *
 * @author hz.lei
 * @since 2018年05月30日 上午12:37
 */
public class HelloProcessor {

    @KafkaListener
    public void foo() {
        System.out.println("你好，我是内部bean，你能调用到吗?");
    }
}
