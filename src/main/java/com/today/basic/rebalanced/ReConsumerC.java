package com.today.basic.rebalanced;


/**
 * 描述: com.today.basic.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月04日 下午12:54
 */
public class ReConsumerC extends ReConsumer {

    public ReConsumerC(String consumerId, String mark) {
        super(consumerId, mark);
    }

    public static void main(String[] args) {
        ReConsumer reConsumer = new ReConsumerB("rebalancedC", "消费者C");
        reConsumer.start();
    }
}
