package com.ce.notebook.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;


/**
 * 基类
 *
 * @author: ce
 * @create: 2018-10-25 19:31
 **/
// 添加@NoRepositoryBean注解, spring初始化不会实例化该类
@NoRepositoryBean
public interface BaseRepository<T, ID> extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

}