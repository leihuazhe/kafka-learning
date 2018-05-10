package com.today.rebalanced;

/**
 * 描述: com.today.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月04日 下午12:54
 */
public class ReConsumerB extends ReConsumer {


    public ReConsumerB(String consumerId, String mark) {
        super(consumerId, mark);
    }


    public static void main(String[] args) {
        ReConsumer reConsumer = new ReConsumerB("rebalancedB", "消费者B");
        reConsumer.start();
    }
}
