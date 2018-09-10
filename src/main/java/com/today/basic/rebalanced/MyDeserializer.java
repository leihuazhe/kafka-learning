package com.today.basic.rebalanced;

import org.apache.kafka.common.serialization.Deserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * desc: MyDeserializer
 *
 * @author hz.lei
 * @since 2018年07月25日 下午6:29
 */
public class MyDeserializer implements Deserializer<Long> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    public void configure(Map<String, ?> configs, boolean isKey) {
        // nothing to do
    }

    public Long deserialize(String topic, byte[] data) {
        if (data == null)
            return null;
        if (data.length != 8) {
            logger.error(" 收到的消息Key不是Long类型,Size of data received by LongDeserializer is not 8,key内容: " + new String(data));
            return -1L;
        }

        long value = 0;
        for (byte b : data) {
            value <<= 8;
            value |= b & 0xFF;
        }
        return value;
    }

    public void close() {
        // nothing to do
    }
}
