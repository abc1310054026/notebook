package com.ce.notebook.controller;

import com.alibaba.fastjson.JSON;
import com.ce.notebook.entity.Page;
import com.ce.notebook.entity.PageSet;
import com.ce.notebook.entity.SysUser;
import com.ce.notebook.service.PageService;
import com.ce.notebook.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * @author: ce
 * @create: 2018-10-27 13:50
 **/
@RestController
@RequestMapping(value = "/pageSet", produces = "application/json;charset=UTF-8")
public class PageSetController extends BaseController<PageSet> {

    @Override
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create (@RequestBody PageSet pageSet) {
        pageSet = service.save(pageSet);
        return JSON.toJSONString(pageSet);
    }
}
