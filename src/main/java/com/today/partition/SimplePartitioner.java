package com.today.partition;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

import java.util.Map;

/**
 * @Desc: SimplePartitioner
 * @author: maple
 * @Date: 2018-01-20 14:25
 */
public class SimplePartitioner implements Partitioner{

    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
        int partiton = 0;
        int iKey = (int) key;
        if(iKey > 0){
//            partiton = iKey % num
        }


        return 0;
    }

    @Override
    public void close() {

    }

    @Override
    public void configure(Map<String, ?> configs) {

    }
}
