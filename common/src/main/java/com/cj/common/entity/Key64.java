package com.cj.common.entity;

import lombok.Data;

@Data
public class Key64 {
    /**
     * admin-user-唯一主键
     */
    private Long id;

    /**
     * 
     */
    private String stub;

    /**
     * admin-user-唯一主键
     * @return id admin-user-唯一主键
     */
    public Long getId() {
        return id;
    }

    /**
     * admin-user-唯一主键
     * @param id admin-user-唯一主键
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 
     * @return stub 
     */
    public String getStub() {
        return stub;
    }

    /**
     * 
     * @param stub 
     */
    public void setStub(String stub) {
        this.stub = stub == null ? null : stub.trim();
    }
}