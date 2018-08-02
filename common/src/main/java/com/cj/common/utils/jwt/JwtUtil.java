package com.cj.common.utils.jwt;

import com.cj.common.entity.AuthRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * jwt工具类
 */
public class JwtUtil {

    //加密、解析秘钥
    private static final String key = "secretkey";

    //加密、解析算法
    private static final SignatureAlgorithm mode = SignatureAlgorithm.HS256;
    /**
     * 生成用户token
     * @param userId
     * @param userName
     * @param userType
     * @return
     */
    public static String getUserToken(long time,long userId,String userName,String userType){
        //设置token，有效期
        String userToken = Jwts.builder()
                .claim("userId",userId)
                .claim("userName",userName)
                .claim("userType",userType)
                .setIssuedAt(new Date(time))
                .signWith(mode,key)
                .compact();

        return userToken;
    }

    /**
     * 生成token
     * @param consumerId 用户ID
     * @param consumerName 用户名
     * @param consumerType 用户类型
     * @param consumerRoles 用户角色
     * @return
     */
    public static String getToken( long consumerId, String consumerName, String consumerType, List<AuthRole> consumerRoles){
        long time = System.currentTimeMillis();
        String token = Jwts.builder()
                .claim("id",consumerId)
                .claim("name",consumerName)
                .claim("type",consumerType)
                .claim("roles",consumerRoles)
                //签发时间
                .setIssuedAt(new Date(time))
                //截止时间
                .setExpiration(new Date(time+1000*60*60*2))
                .signWith(mode,key)
                .compact();

        return token;
    }
    /**
     * 生成管理员token
     * @param time
     * @param adminId
     * @param adminName
     * @param adminType
     * @return
     */
    public static String getAdminToken(long time,long adminId,String adminName,String adminType){
        //设置token，有效期
        String AdminToken = Jwts.builder()
                .claim("adminId",adminId)
                .claim("adminName",adminName)
                .claim("adminType",adminType)
                .setIssuedAt(new Date(time))
//                    .setExpiration(new Date(time+1000*60*60*2))
                .signWith(mode,key)
                .compact();

        return AdminToken;
    }

    /**
     * 解析token
     * @param token
     * @return 解析失败返回null
     *
     */
    public static Claims getClaims(String token, HttpServletRequest request){
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();

            if (request != null) {
                request.getSession().setAttribute("id", (Integer) claims.get("id"));
                request.getSession().setAttribute("name", (String) claims.get("name"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("=============claims===========================");
        System.out.println(claims);
        System.out.println("=============claims===========================");

        return claims;
    }

    public static String getVerifyCodetoken(String code){
        Date date = new Date();
        long time = date.getTime();
        String token = Jwts.builder()
                .claim("code", code)
                .setIssuedAt(date)
                //设置token过期时间戳
                .setExpiration(new Date(time+1000*60*2))
                .signWith(mode,key)
                .compact();
        return token;
    }
    public static  Claims getVerifyCodeClaims(String token){
        Claims claims=null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }
}
