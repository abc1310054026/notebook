package com.ce.notebook.configuration.jpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;
import java.util.Map;

/**
 * 实体类对应Repository类配置
 *
 * @author: ce
 * @create: 2018-10-26 00:07
 **/
public class EntityToRepository {

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class, Class> ClassToRepositoryMap = new HashMap<>();

    public void put (Class entity, Class repository) {
        ClassToRepositoryMap.put(entity, repository);
    }

    public Class getRepository (Class clazz) {
        return ClassToRepositoryMap.get(clazz);
    }
}
