package com.today.basic.annotation;

import java.lang.annotation.*;

/**
 * @Desc: ConsumerListener
 * @author: maple
 * @Date: 2018-01-23 11:50
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //用于子类继承该注解
@Documented
public @interface ConsumerListener {

    String topic() default "";
}


