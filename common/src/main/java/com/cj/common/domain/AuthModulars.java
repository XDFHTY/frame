package com.cj.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统所欲权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthModulars implements Serializable {

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
     * 子节点
     */

    private List<AuthModulars> children;

//
//
//    public AuthModulars deepClone() {
//        AuthModulars clone = new AuthModulars();
//        clone.modularId = this.modularId;
//        clone.pageName = this.pageName;
//        clone.pageUrl = this.pageUrl;
//        clone.pageMethod = this.pageMethod;
//        clone.pageType = this.pageType;
//        clone.pageSort = this.pageSort;
//        clone.parentId = this.parentId;
//        clone.spare1 = this.spare1;
//        clone.state = this.state;
//        clone.createTime = this.createTime;
////        if (this.children != null && this.children.size() > 0) {
//            clone.children = new ArrayList<>(this.children);
////        }
//        return clone;
//    }
//
//    /**
//     * 浅复制
//     * @return
//     */
//    @Override
//    public AuthModulars clone() {
//        AuthModulars o = null;
//        try {
//            o = (AuthModulars)super.clone();
//        } catch(CloneNotSupportedException e) {
//            System.out.println(e.toString());
//        }
//        return o;
//    }
//
//    /**
//     * 深复制
//     * @return
//     */
//    public AuthModulars sclone() {
//        AuthModulars o = null;
//        try {
//            o = (AuthModulars) super.clone();
//            //对引用的对象也进行复制
//            o = deepClone().clone();
//        } catch (CloneNotSupportedException e) {
//            System.out.println(e.toString());
//        }
//        return o;
//    }

}
