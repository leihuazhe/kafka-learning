package com.today.basic.rebalanced;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

/**
 * 描述: com.today.basic.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月08日 上午1:11
 */
public class TimeTest {

    public static void main(String[] args) {

        LocalDateTime ctt = LocalDateTime.ofInstant(Instant.ofEpochMilli(1525711725737L), ZoneId.of("Asia/Shanghai"));

        System.out.println(ctt);


    }
}
