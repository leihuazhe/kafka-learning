package com.today.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @Desc: KafkaTest
 * @author: maple
 * @Date: 2018-01-23 11:19
 */
public class KafkaTest {

    @Test
    public void test1() {
        Class clazz = IHelloService.class;
        Class msgConsumerClass = null;
        Class consumerListenerClass = null;
        try {
            msgConsumerClass = clazz.getClassLoader().loadClass("com.today.annotation.MsgConsumer");
            consumerListenerClass = clazz.getClassLoader().loadClass("com.today.annotation.ConsumerListener");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(clazz.isAnnotationPresent(msgConsumerClass)) {

            Annotation msgConsumer = clazz.getAnnotation(msgConsumerClass);
            try {
//                msgConsumer.groupId(); 不使用 反射
               Method method =  msgConsumer.getClass().getDeclaredMethod("groupId");
               String groupId = (String) method.invoke(msgConsumer);

               System.out.println(groupId);

            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }

        }

        Method[] methods =  IHelloService.class.getMethods();
        for(Method method : methods) {
            String methodName = method.getName();
            if(method.isAnnotationPresent(consumerListenerClass)) {
                Annotation consumerListener = method.getAnnotation(consumerListenerClass);
                try {
                    String topic = (String) consumerListener.getClass().getDeclaredMethod("topic").invoke(consumerListener);
                    System.out.println(topic);



                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }

            }




        }


    }


}
