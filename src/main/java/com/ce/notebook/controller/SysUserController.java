package com.ce.notebook.controller;

import com.ce.notebook.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: ce
 * @create: 2018-11-02 08:19
 **/
@RestController
@RequestMapping(value = "/sysUser", produces = "application/json;charset=UTF-8")
public class SysUserController extends BaseController<SysUser> {
}
