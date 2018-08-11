package com.cj.common.security.filter;

import com.cj.common.utils.domain.ApiResult;
import com.cj.common.utils.http.HttpUtil;
import com.cj.common.utils.jwt.JwtUtil;
import com.cj.common.security.dto.Customer;
import com.cj.common.security.dto.TokenUserAuthentication;
import com.cj.common.utils.domain.MemoryData;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 *  jwt token验证类,验证成功后设置进去SecurityContext中
 * @author Niu Li
 * @since 2017/6/28
 */
@Slf4j
public class LoginFilter extends OncePerRequestFilter {




  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
    //前端提交的token
    Claims claims=null;
    Integer id=null;
    ApiResult apiResult=null;
    //获取用户提交的token
    String newToken =request.getHeader("token");
    Optional<Authentication> authentication = null;
    //用户未上传token
    if (newToken == null || newToken.trim().length() == 0) {
      apiResult = ApiResult.CODE_401;
      apiResult.setMsg("用户未提交token");
      HttpUtil.doReturn(response,apiResult);
      authentication = Optional.empty();
      SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
//      filterChain.doFilter(request,response);
      return ;
    }
    log.info("token"+newToken);
    //存储内存中的token
    String oldToken=null;
    //解析用户token
    claims=JwtUtil.getClaims(newToken,request);
    //token解析失败
    if (null == claims) {
      // ApiResult apiResult = ApiResult.CODE_401;
      //如果token解析不正确
      //返回401
      apiResult = ApiResult.CODE_401;
      apiResult.setMsg("token不存在");
      HttpUtil.doReturn(response,apiResult);
      authentication = Optional.empty();
//      filterChain.doFilter(request,response);
      SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
      return ;
    }



    //解析用户名
    id = (Integer) claims.get("id");
    log.info(MemoryData.getTokenMap().get(id.toString()));
    Map map=MemoryData.getTokenMap();
    //当前用户的tmemorydata中的token
    if (MemoryData.getTokenMap().containsKey(id.toString())){
      //初始化memorydata内存中的token
      oldToken = MemoryData.getTokenMap().get(id.toString());

    }
    //token过期
    if (oldToken == null) {
      //返回http信息
      apiResult = ApiResult.CODE_401;
      apiResult.setMsg("token已过期");
      HttpUtil.doReturn(response,apiResult);
      //存储验证失败信息
      authentication = Optional.empty();
      SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
      //执行下一个过滤连
//      filterChain.doFilter(request,response);
      return;
    }
    if (oldToken.equals(newToken)) {
      Customer customer = new Customer();
      customer.setCustomerId(new Long((Integer) claims.get("id")));
      customer.setCustomerName((String) claims.get("name"));
      customer.setCustomerType((String) claims.get("type"));
      //存储用户的基本信息和角色信息到上下文
      List<Map> roles = new ArrayList<>();
      roles.addAll((List) claims.get("roles"));
      authentication = Optional.of(new TokenUserAuthentication(customer, roles, true));
      SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
      filterChain.doFilter(request, response);
      //token 不相同
    } else {
      //如果token解析不正确
      //返回401
      apiResult = ApiResult.CODE_401;
      apiResult.setMsg("token已失效");
      HttpUtil.doReturn(response,apiResult);
      authentication = Optional.empty();
//      filterChain.doFilter(request,response);
      SecurityContextHolder.getContext().setAuthentication(authentication.orElse(null));
      return ;
    }


  }
}
