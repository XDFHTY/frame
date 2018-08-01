package com.cj.common.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 系统所有角色的所有权限
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRoleModulars {

    //角色ID
    private Long roleId;

    //角色名称
    private String roleName;

    //角色状态
    private String roleState;



    /**
     * 角色所有的权限ID
     */
    private List<Modular> modularIds;

    /**
     * 角色下所有权限
     */
    private AuthModulars authModulars;
}
