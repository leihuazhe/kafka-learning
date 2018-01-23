package com.today.spring;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Desc: TODO
 * author: maple
 * Date: 2018-01-22 18:53
 */
public class SpringKafkaRunner implements ApplicationListener<ContextRefreshedEvent> {


    @Override
    public void onApplicationEvent(ContextRefreshedEvent context) {

    }
}
