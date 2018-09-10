package com.today.basic.proxy;

/**
 * 描述:
 *
 * @author hz.lei
 * @date 2018年04月07日 下午10:56
 */
public class UserServiceImpl implements UserService {


    @Override
    public String selectUserById(int id) {
        return "leihuazhe,age: " + id;
    }
}
