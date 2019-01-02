package com.ce.notebook.configuration.jpa;

import com.ce.notebook.domain.PageRepository;
import com.ce.notebook.domain.PageSetRepository;
import com.ce.notebook.domain.SysUserRepository;
import com.ce.notebook.entity.Page;
import com.ce.notebook.entity.PageSet;
import com.ce.notebook.entity.SysUser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;


/**
 * JPA数据库操作配置
 *
 * @author: ce
 * @create: 2018-10-26 00:33
 **/
@Configuration
public class JPAConfig {

    @Bean
    public EntityToRepository entityToRepository () {
        EntityToRepository entityToRepository = new EntityToRepository();
        Map<Class, Class> map = new HashMap<>();
        map.put(Page.class, PageRepository.class);
        map.put(PageSet.class, PageSetRepository.class);
        map.put(SysUser.class, SysUserRepository.class);
        for (Map.Entry<Class, Class> entry : map.entrySet()) {
            entityToRepository.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey());
        }
        return entityToRepository;
    }
}
