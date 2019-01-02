package com.ce.notebook.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ce.notebook.configuration.controller.EntityToService;
import com.ce.notebook.service.BaseService;
import com.ce.notebook.utils.ObjectUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.lang.reflect.ParameterizedType;
import java.util.List;

/**
 * controller基类
 *
 * @author: ce
 * @create: 2018-10-27 19:39
 **/
@RestController
public class BaseController<T> {

    private static final Logger logger = LoggerFactory.getLogger(BaseController.class);

    @Autowired
    private EntityToService entityToService;
    @Autowired
    private ApplicationContext applicationContext;

    public BaseService<T> service = null;

    /*
     * 获取T的实际类对象
     * @author ce
     * @date 18-10-26 下午8:05
     * @param []
     * @return java.lang.Class
     */
    private Class getTClass() {
        if (getClass().getGenericSuperclass() instanceof ParameterizedType) {
            ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
            if (type.getActualTypeArguments().length <= 0) {
                return null;
            }
            return (Class) type.getActualTypeArguments()[0];
        }
        return null;
    }

    @PostConstruct
    public void init () {
        Class clazz = getTClass();
        if (clazz != null) {
            service = (BaseService<T>) applicationContext.getBean(entityToService.getService(clazz));
        }
    }

    /*
     * 通用新建接口
     * @author ce
     * @date 18-10-29 下午4:35
     * @param [t]
     * @return java.lang.String
    */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String create (@RequestBody T t) {
        t = service.save(t);
        return JSON.toJSONString(t);
    }

    /*
     * 通用查询接口, url参数为空表示查询所有
     * url参数可以为空需要设置两个value值,一个带参数{arg},一个不带的.然后将@PathVariable的required设置为false
     * @author ce
     * @date 18-10-28 上午12:45
     * @param [id]
     * @return java.lang.String
    */
    @RequestMapping(value = {"/query", "/query/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String query (@PathVariable(required = false) Long id, @RequestBody(required = false) List<T> ts) {
        Object result = null;
//        url传参
        if (id != null) result = service.findById(id);
//        请求体内传参
        else if (ts != null && ts.size() > 0) {
            if (ts.size() > 1) {
                List<Long> list = ObjectUtils.getFieldValue(ts.toArray(), "id");
                result = service.findAllById(list);
            } else {
                result = service.findAll(ts.get(0));
            }
        }
//        无参数
        else result = service.findAll();
//        返回json字符串, 禁止fastjson循环引用
        return JSON.toJSONString(result, SerializerFeature.DisableCircularReferenceDetect);
    }

    /*
     * 通用更新接口
     * @author ce
     * @date 18-10-29 下午4:38
     * @param [t]
     * @return java.lang.String
    */
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    public String update (@RequestBody T t) {

        return JSON.toJSONString(service.update(t));
    }

    /*
     * 通用删除接口
     * @author ce
     * @date 18-10-29 下午4:39
     * @param [id, ts]
     * @return java.lang.String
    */
    @RequestMapping(value = {"/delete/{id}", "/delete"}, method = RequestMethod.DELETE)
    public String delete (@PathVariable(required = false) Long id, @RequestBody(required = false) List<T> ts) {
        if (id != null) service.deleteById(id);
        else if (ts != null && ts.size() > 0) {
            service.deleteAll(ts);
        }
        return null;
    }
}
