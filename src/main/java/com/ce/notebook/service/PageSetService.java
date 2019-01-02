package com.ce.notebook.service;

import com.ce.notebook.domain.PageRepository;
import com.ce.notebook.entity.Page;
import com.ce.notebook.entity.PageSet;
import com.ce.notebook.entity.SysUser;
import com.ce.notebook.utils.SecurityUtils;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.Optional;

/**
 * @author: ce
 * @create: 2018-10-27 13:50
 **/
@Service
public class PageSetService extends BaseService<PageSet> {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private PageService pageService;

    @Override
    public PageSet save(PageSet pageSet) {
        return create(pageSet);
    }

    public PageSet create (PageSet pageSet) {
        pageSet = super.save(pageSet);
        SysUser currentUser = SecurityUtils.getCurrentUser();

        Page page = new Page();
        page.setTitle(pageSet.getTitle());
        page.setAuthor(currentUser.getUsername());
        page.setParentId(-1L);
        page.setPageSetId(pageSet.getId());
        page = pageService.save(page);
        pageService.repository.flush();
        pageSet.setRootPageId(page.getId());
        pageSet = update(pageSet);
        super.save(pageSet);

        /*
        * 清除session缓存 */
        entityManager.unwrap(Session.class).clear();
        return findById(pageSet.getId());
    }
}
