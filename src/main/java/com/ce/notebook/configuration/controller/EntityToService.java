package com.ce.notebook.configuration.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体类对应Service类配置
 *
 * @author: ce
 * @create: 2018-10-27 19:45
 **/
public class EntityToService {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class, Class> entityToServiceMap = new HashMap<>();

    public void put (Class entity, Class repository) {
        entityToServiceMap.put(entity, repository);
    }

    public Class getService (Class clazz) {
        return entityToServiceMap.get(clazz);
    }
}
