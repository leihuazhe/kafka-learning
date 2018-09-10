package com.today.eventbus.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author maple 2018.09.10 下午1:43
 */
public class OffsetManager {
    private static final String STORAGE_PREFIX = "DAPENG";

    private static final Logger LOGGER = LoggerFactory.getLogger(OffsetManager.class);


    /**
     * Overwrite the offset for the topic in an external storage.
     *
     * @param topic     - Topic name.
     * @param partition - Partition of the topic.
     * @param offset    - offset to be stored.
     */
    void saveOffsetInExternalStore(String topic, int partition, long offset) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(storageName(topic, partition), false))) {
            bufferedWriter.write(offset + "");
            bufferedWriter.flush();
            LOGGER.info("=================>save topic: {}, partition:{}, offset:{}", topic, partition, offset);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }

    }

    /**
     * @return he last offset + 1 for the provided topic and partition.
     */
    long readOffsetFromExternalStore(String topic, int partition) {
        try {
            Stream<String> stream = Files.lines(Paths.get(storageName(topic, partition)));

            long offset = Long.parseLong(stream.collect(Collectors.toList()).get(0));
            LOGGER.info("=================>read topic: {}, partition:{}, offset:{}", topic, partition, offset);
            return offset;
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return 0;
    }


    /**
     * storageName 存储名称
     *
     * @param topic     topic
     * @param partition partition
     * @return key
     */
    private String storageName(String topic, int partition) {
        String storageKey = STORAGE_PREFIX + "-" + topic + "-" + partition;
        LOGGER.info("storageName-key: {}", storageKey);
        return storageKey;
    }
}
