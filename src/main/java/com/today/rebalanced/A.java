package com.today.rebalanced;

import com.github.dapeng.core.SoaException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 描述: com.today.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月06日 下午10:48
 */
public class A {

    public void foo() throws SoaException {
        System.out.println("foo...");
        throw new SoaException("故意异常！", "异常测试");
    }

    public static void main(String[] args) throws IllegalAccessException, InstantiationException, NoSuchMethodException {
        /*List list = new ArrayList();
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        list.add("1");
        List list1 = (List) list.stream().limit(4).collect(Collectors.toList());
        System.out.println(list1);*/

        A a = new A();

        Class clazz = A.class;
//        Object o = clazz.newInstance();
        Method foo = clazz.getMethod("foo");
        try {
            Object invoke = foo.invoke(a);
        } catch (InvocationTargetException e) {
            System.out.println(e.getTargetException() == e.getTargetException().getCause());
            System.out.println("e: " + e);
            System.out.println("e: " + e.getCause());
            System.out.println("e: " + e.getTargetException());
            System.out.println("e: " + e.getTargetException().getCause());
        }

    }

}
