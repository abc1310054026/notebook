package com.ce.notebook.service;

import com.ce.notebook.domain.PageRepository;
import com.ce.notebook.entity.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Collection;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class PageServiceTest {

    @Autowired
    PageRepository pageRepository;
    @Test
    public void findByTitle() {
        Collection<Page.pageTitle> collection = pageRepository.findByAuthor("ce");
        for (Page.pageTitle title : collection) {
            System.out.println(title.getTitle());
        }
        System.out.println(collection);
    }
}