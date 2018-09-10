package com.today.basic.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 描述:
 *
 * @author hz.lei
 * @date 2018年04月07日 下午10:58
 */
public class UserviceProxy implements InvocationHandler {

    private Object target;

    public UserviceProxy(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("begin transaction");
        System.out.println(1 + proxy.getClass().getName());
        Object result = null;
        try {
            //注意这里是目标对象
            result = method.invoke(target, args);
            System.out.println("commit transaction");
        } catch (Exception e) {
            System.out.println("rollback transaction");
        }
        return result;
    }

    public Object getProxy() {
        Object proxy = Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(), this);
        System.out.println(proxy.getClass().getName());
        return proxy;
    }


}
