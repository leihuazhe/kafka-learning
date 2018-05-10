package com.today.proxy;

/**
 * 描述:
 *
 * @author hz.lei
 * @date 2018年04月07日 下午11:04
 */
public class Test {

    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        UserviceProxy proxy = new UserviceProxy(userService);
        UserService userService1 = (UserService) proxy.getProxy();
        String s = userService1.selectUserById(5);
        System.out.println(s);
    }
}
