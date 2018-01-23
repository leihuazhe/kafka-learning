package com.today.annotation;

import java.lang.annotation.*;

/**
 * Desc: TODO
 * author: maple
 * Date: 2018-01-23 11:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited //用于子类继承该注解
@Documented
public @interface MsgConsumer {

    String groupId() default "";

    String zkHost() default "127.0.0.1";
}
