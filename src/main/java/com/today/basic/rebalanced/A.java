package com.today.basic.rebalanced;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 描述: com.today.basic.rebalanced
 *
 * @author hz.lei
 * @date 2018年05月06日 下午10:48
 */
public class A {

    public void foo() {
        System.out.println("foo...");
        throw new RuntimeException("故意异常！");
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
