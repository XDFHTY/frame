package com.cj.common.security.dto;

import com.cj.common.entity.AuthRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Spring Security中存放的认证用户
 * @author Niu Li
 * @since 2017/6/28
 */
public class TokenUserAuthentication implements Authentication {

  private static final long serialVersionUID = 3730332217518791533L;

  private Customer customer;
  private List<Map> roles =null;
  private Boolean authentication = false;

  public TokenUserAuthentication(Customer customer,List<Map> roles, Boolean authentication) {
    this.customer = customer;
    this.roles=roles;
    this.authentication = authentication;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    //获取用户角色权限
    List<SimpleGrantedAuthority> templist = new ArrayList();

    for (Map role:roles){
      templist.add(new SimpleGrantedAuthority((String) role.get("roleName")));
    }
    return templist;
/*    return templist.stream()
        .map(SimpleGrantedAuthority::new).collect(Collectors.toList());*/
  }

  @Override
  public Object getCredentials() {
    return "";
  }

  @Override
  public Object getDetails() {
    return customer;
  }

  @Override
  public Object getPrincipal() {
    return customer.getCustomerId();
  }

  @Override
  public boolean isAuthenticated() {
    return authentication;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.authentication = isAuthenticated;
  }

  @Override
  public String getName() {
    return customer.getCustomerName();
  }
}
