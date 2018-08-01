package com.cj.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class AuthModular {
    /**
     * 权限资源表
     */
    private Integer modularId;

    /**
     * 名称
     */
    private String pageName;

    /**
     * 请求路径
     */
    private String pageUrl;

    /**
     * 请求方式resful方法头
     */
    private String pageMethod;

    /**
     * 分类，1-一级目录，0-请求
     */
    private String pageType;

    /**
     * 排序
     */
    private Integer pageSort;

    /**
     * 父级页面ID
     */
    private Integer parentId;

    /**
     * 备用
     */
    private String spare1;

    /**
     * 状态（0-已删除，1-正常）
     */
    private String state;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 权限资源表
     * @return modular_id 权限资源表
     */
    public Integer getModularId() {
        return modularId;
    }

    /**
     * 权限资源表
     * @param modularId 权限资源表
     */
    public void setModularId(Integer modularId) {
        this.modularId = modularId;
    }

    /**
     * 名称
     * @return page_name 名称
     */
    public String getPageName() {
        return pageName;
    }

    /**
     * 名称
     * @param pageName 名称
     */
    public void setPageName(String pageName) {
        this.pageName = pageName == null ? null : pageName.trim();
    }

    /**
     * 请求路径
     * @return page_url 请求路径
     */
    public String getPageUrl() {
        return pageUrl;
    }

    /**
     * 请求路径
     * @param pageUrl 请求路径
     */
    public void setPageUrl(String pageUrl) {
        this.pageUrl = pageUrl == null ? null : pageUrl.trim();
    }

    /**
     * 请求方式resful方法头
     * @return page_method 请求方式resful方法头
     */
    public String getPageMethod() {
        return pageMethod;
    }

    /**
     * 请求方式resful方法头
     * @param pageMethod 请求方式resful方法头
     */
    public void setPageMethod(String pageMethod) {
        this.pageMethod = pageMethod == null ? null : pageMethod.trim();
    }

    /**
     * 分类，1-一级目录，0-请求
     * @return page_type 分类，1-一级目录，0-请求
     */
    public String getPageType() {
        return pageType;
    }

    /**
     * 分类，1-一级目录，0-请求
     * @param pageType 分类，1-一级目录，0-请求
     */
    public void setPageType(String pageType) {
        this.pageType = pageType == null ? null : pageType.trim();
    }

    /**
     * 排序
     * @return page_sort 排序
     */
    public Integer getPageSort() {
        return pageSort;
    }

    /**
     * 排序
     * @param pageSort 排序
     */
    public void setPageSort(Integer pageSort) {
        this.pageSort = pageSort;
    }

    /**
     * 父级页面ID
     * @return parent_id 父级页面ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 父级页面ID
     * @param parentId 父级页面ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 备用
     * @return spare1 备用
     */
    public String getSpare1() {
        return spare1;
    }

    /**
     * 备用
     * @param spare1 备用
     */
    public void setSpare1(String spare1) {
        this.spare1 = spare1 == null ? null : spare1.trim();
    }

    /**
     * 状态（0-已删除，1-正常）
     * @return state 状态（0-已删除，1-正常）
     */
    public String getState() {
        return state;
    }

    /**
     * 状态（0-已删除，1-正常）
     * @param state 状态（0-已删除，1-正常）
     */
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    /**
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}