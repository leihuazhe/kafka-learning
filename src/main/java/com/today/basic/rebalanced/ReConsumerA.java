package com.today.basic.rebalanced;


/**
 * 描述: com.today.basic.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月04日 下午12:54
 */
public class ReConsumerA extends ReConsumer {


    public ReConsumerA(String consumerId, String mark) {
        super(consumerId, mark);
    }


    public static void main(String[] args) {
        ReConsumer reConsumer = new ReConsumerA("rebalancedA", "消费者A");
        reConsumer.start();
    }
}
