package com.ce.notebook.entity;

import javax.persistence.*;
import java.util.Date;

/**
 * 文章信息
 *
 * @author: ce
 * @create: 2018-10-22 21:02
 **/
@Entity
public class Page {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = true)
    private String content;
    @Column(nullable = true)
    private Date createTime;
    @Column(nullable = false)
    private Date lastModifiedTime;
    @Column(nullable = false)
    private Long parentId;
    @Column(nullable = false)
    private Long pageSetId;

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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Long getPageSetId() {
        return pageSetId;
    }

    public void setPageSetId(Long pageSetId) {
        this.pageSetId = pageSetId;
    }

    @Override
    public String toString() {
        return "Page{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", lastModifiedTime=" + lastModifiedTime +
                ", parentId=" + parentId +
                ", pageSetId=" + pageSetId +
                '}';
    }

    public interface pageTitle {
        Long getId ();
        String getTitle ();
    }
}
