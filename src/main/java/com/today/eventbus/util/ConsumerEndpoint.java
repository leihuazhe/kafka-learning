package com.today.eventbus.util;


import com.github.dapeng.core.BeanSerializer;

import java.lang.reflect.Method;
import java.util.List;

/**
 * 描述: 业务关心 consumer 上下文
 *
 * @author hz.lei
 * @since 2018年03月02日 上午1:18
 */
public class ConsumerEndpoint {
    private String id;

    public ConsumerEndpoint(String id) {
        this.id = id;
    }
}
