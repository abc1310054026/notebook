package com.ce.notebook.configuration.controller;

import com.ce.notebook.entity.Page;
import com.ce.notebook.entity.PageSet;
import com.ce.notebook.entity.SysUser;
import com.ce.notebook.service.PageService;
import com.ce.notebook.service.PageSetService;
import com.ce.notebook.service.SysUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: ce
 * @create: 2018-10-27 19:47
 **/
@Configuration
public class ControllerConfig {

    @Bean
    public EntityToService entityToService () {
        EntityToService entityToRepository = new EntityToService();
        Map<Class, Class> map = new HashMap<>();
        map.put(Page.class, PageService.class);
        map.put(PageSet.class, PageSetService.class);
        map.put(SysUser.class, SysUserService.class);
        for (Map.Entry<Class, Class> entry : map.entrySet()) {
            entityToRepository.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey());
        }
        return entityToRepository;
    }

}
