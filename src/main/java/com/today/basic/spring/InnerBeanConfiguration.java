package com.today.basic.spring;

import com.today.basic.spring.bean.HelloProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * desc: TODO
 *
 * @author hz.lei
 * @since 2018年05月30日 上午12:36
 */
@Configuration
public class InnerBeanConfiguration {


    @Bean
    public HelloProcessor getHelloProcessor() {
        return new HelloProcessor();
    }

}
