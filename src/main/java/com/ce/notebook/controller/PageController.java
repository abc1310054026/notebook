package com.ce.notebook.controller;

import com.ce.notebook.entity.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 有关Page类的对外API
 *
 * @author: ce
 * @create: 2018-10-22 21:11
 **/
@RestController
@RequestMapping(value = "/page", produces = "application/json;charset=UTF-8")
public class PageController extends BaseController<Page> {

}