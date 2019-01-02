package com.ce.notebook.entity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.annotation.JSONField;

import javax.persistence.*;
import java.util.*;

/**
 * page类的集合
 *
 * @author ce
 * @create 2018-10-24 22:49
 **/
@Entity
public class PageSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private Long rootPageId;
    @Column(nullable = true)
    private String description;
    @Column(nullable = false)
    private Date createTime;
    @Column(nullable = false)
    private Date lastModifiedTime;

    /*

    * JSONField: serialize 设置为false,生成的json字符串不会包含该字段
    * */
    @JSONField(serialize = false)
    @OneToMany
    @JoinColumn(name = "pageSetId")
    private Set<Page> pages;

    @JSONField(serialize = false)
    @Transient
    private Map<Long, Set<Page>> pageMap;

    @Transient
    private JSONArray pageTree;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getRootPageId() {
        return rootPageId;
    }

    public void setRootPageId(Long rootPageId) {
        this.rootPageId = rootPageId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }

    public Set<Page> getPages() {
        return pages;
    }

    public void setPages(Set<Page> pages) {
        this.pages = pages;
    }

    public Map<Long, Set<Page>> getPageMap() {
        if (this.pageMap == null) {
            initTree();
        }
        return pageMap;
    }

    public void setPageMap(Map<Long, Set<Page>> pageMap) {
        this.pageMap = pageMap;
    }

    public JSONArray getPageTree() {
        if (this.pageTree == null) {
            initTree();
        }
        return pageTree;
    }

    public void setPageTree(JSONArray pageTree) {
        this.pageTree = pageTree;
    }

    @Override
    public String toString() {
        return "PageSet{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", rootPageId=" + rootPageId +
                ", description='" + description + '\'' +
                ", createtime=" + createTime +
                ", lastModifiedTime=" + lastModifiedTime +
                '}';
    }

    public void initTree () {
        createPageMap();
        createPageTree();
    }

    public void createPageMap () {
        pageMap = new HashMap<>();
        Set<Page> pages = getPages();
        for (Page page : pages) {
            pageMap.computeIfAbsent(page.getParentId(), k -> new HashSet<>());
            pageMap.get(page.getParentId()).add(page);
        }
    }

    public void createPageTree () {
//        if (getPageMap().get(getRootPageId()) != null)
        setPageTree(dfs(getPageMap(), -1L));
    }

    /**
     * 深搜生成wiki树，未进行marked检测
     * @author Ce
     * @date 2018/4/23 19:00
     * @param [wikiTree, pageId]
     * @return com.alibaba.fastjson.JSONArray
     */
    private JSONArray dfs (Map<Long, Set<Page>> pageMap, Long id) {
        JSONArray jsonArray = new JSONArray();

        for (Page page : pageMap.get(id)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("page", page);
            if (pageMap.get(page.getId()) != null) jsonObject.put("childPage", dfs(pageMap, page.getId()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
}
