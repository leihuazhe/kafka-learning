package com.today.annotation;

/**
 * @Desc: IHelloService
 * @author: maple
 * @Date: 2018-01-23 11:12
 */
@MsgConsumer(groupId = "supplier")
public interface IHelloService {

    void sayHello(String msg);

    @ConsumerListener(topic = "foo")
    String foo(int acc, int acc2);

}
