package com.ce.notebook.service;

import com.ce.notebook.domain.PageRepository;
import com.ce.notebook.entity.Page;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class BaseServiceTest {

    @Autowired
    PageRepository pageRepository;
    @Autowired
    PageService pageService;
    @Test
    public void save() {
    }

    @Test
    public void findAll() {
        Iterable<Page> pages = pageRepository.findAll((Specification<Page>) (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();

            list.add(criteriaBuilder.equal(root.get("id"), 14L));
            criteriaQuery.where(criteriaBuilder.and(list.toArray(new Predicate[1])));
            return criteriaQuery.getRestriction();
        });
        System.out.println(pages);
    }

    @Test
    public void findAll1() {
    }
}