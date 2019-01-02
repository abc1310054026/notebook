package com.ce.notebook.service;

import com.ce.notebook.entity.Page;
import org.springframework.stereotype.Service;

import java.util.Date;


/**
 * @author: ce
 * @create: 2018-10-22 21:35
 **/
@Service
public class PageService extends BaseService<Page> {

    /*
     * page更新操作,重设最后更新时间
     * @author ce
     * @date 18-11-2 上午7:44
     * @param [page]
     * @return com.ce.notebook.entity.Page
    */
    @Override
    public Page update(Page page) {
        page.setLastModifiedTime(new Date());
        return super.update(page);
    }
}
