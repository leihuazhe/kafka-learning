package com.today.basic.algorithm;

import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Denim.leihz 2019-12-27 9:27 AM
 */
public class RoundRobinTest {

    private static final ConcurrentMap<String, AtomicInteger> topicCounterMap = new ConcurrentHashMap<>();

    private static final Map<String, List<PartitionInfo>> TOPICS = new ConcurrentHashMap<>();

    /**
     * Compute the partition for the given record.
     *
     * @param topic   The topic name
     * @param cluster The current cluster metadata
     */
    public int partition(String topic) {
        List<PartitionInfo> partitions = TOPICS.get(topic);
        int numPartitions = partitions.size();
        int nextValue = nextValue(topic);

        List<PartitionInfo> availablePartitions = partitions;
        if (!availablePartitions.isEmpty()) {
            int part = Utils.toPositive(nextValue) % availablePartitions.size();
            return availablePartitions.get(part).partition();
        } else {
            // no partitions are available, give a non-available partition
            return Utils.toPositive(nextValue) % numPartitions;
        }
    }

    private int nextValue(String topic) {
        AtomicInteger counter = topicCounterMap.computeIfAbsent(topic, k -> new AtomicInteger(0));
        return counter.getAndIncrement();
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
        AtomicInteger atomicInteger = new AtomicInteger(2147483647);
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());
        System.out.println(atomicInteger.incrementAndGet());

        atomicInteger.addAndGet(2147000000);
        int i1 = atomicInteger.incrementAndGet();
        System.out.println(i1 & 0x7fffffff);
        System.out.println(i1);

        int i2 = atomicInteger.incrementAndGet();
        System.out.println(i2 & 0x7fffffff);
        System.out.println(i2);

        for (int i = 0; i < 2147483647; i++) {


            System.out.println(atomicInteger.incrementAndGet());
        }


        TOPICS.put("feature", new ArrayList<PartitionInfo>() {{
            add(new PartitionInfo("feature", 0, null, null, null));
            add(new PartitionInfo("feature", 1, null, null, null));
            add(new PartitionInfo("feature", 2, null, null, null));
            add(new PartitionInfo("feature", 3, null, null, null));
        }});

        RoundRobinTest test = new RoundRobinTest();

        for (int i = 0; i < 100; i++) {
            int feature = test.partition("feature");

            System.out.println("p: " + feature);
        }

    }
}
