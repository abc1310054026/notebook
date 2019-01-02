package com.ce.notebook.domain;

import com.ce.notebook.entity.Page;
import org.springframework.stereotype.Repository;

import java.util.Collection;


/**
 * 对数据库中Page类的操作
 *
 * @author: ce
 * @create: 2018-10-22 21:08
 **/
@Repository
public interface PageRepository extends BaseRepository<Page, Long> {
    public Collection<Page.pageTitle> findByAuthor (String author);
}
