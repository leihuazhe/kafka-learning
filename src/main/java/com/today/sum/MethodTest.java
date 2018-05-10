package com.today.sum;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述: com.today.sum
 *
 * @author hz.lei
 * @date 2018年05月10日 上午9:17
 */
public class MethodTest {

    Map map = new HashMap<>();


    public void doSomething() {
        int util = util(10, map);

        System.out.println(util);

        map.forEach(((k,v)-> {
            System.out.println(k+":"+v);
        }));

    }

    public static int util(int a, Map map) {

        map.put("1", 2);

        return a + 2;
    }

    public static void main(String[] args) {
        MethodTest test = new MethodTest();

        test.doSomething();

    }

}
