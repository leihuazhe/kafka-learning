package com.yunji.kafka.consumer;

import java.util.regex.Pattern;

/**
 * @author Denim.leihz 2019-12-30 4:08 PM
 */
public class PatternTest {

    private static final Pattern PATTERN = Pattern.compile(".*?/_jagent/health/check");

    public static void main(String[] args) {
        boolean matches = PATTERN.matcher("/yunjiuserapp/xxx/_jagent/health/check").matches();

        System.out.println(matches);
    }
}
