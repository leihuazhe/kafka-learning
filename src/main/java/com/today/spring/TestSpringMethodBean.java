package com.today.spring;

import org.omg.PortableInterceptor.SYSTEM_EXCEPTION;

import java.util.Properties;

/**
 * 描述:
 *
 * @author hz.lei
 * @date 2018年04月13日 上午12:28
 */
public class TestSpringMethodBean {

    public static void main(String[] args) {
        Properties properties = System.getProperties();
        Object version = properties.get("java.version");
        properties.put("kafka", "9092");
        String kafka = System.getenv("kafka");
        String kafka1 = System.getProperty("kafka");
        System.out.println("kafka   " + kafka + "  kafka1  " + kafka1);
        System.out.println(version);

    }
}
