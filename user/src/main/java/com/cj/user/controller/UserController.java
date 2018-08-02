package com.cj.user.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.cj.common.domain.RegisterInfo;
import com.cj.core.aop.Log;
import com.cj.common.entity.User;
import com.cj.common.utils.entity.ApiResult;
import com.cj.common.utils.entity.ExpiryMap;
import com.cj.common.utils.jwt.JwtUtil;
import com.cj.common.utils.util.MemoryVerifyCode;
import com.cj.common.utils.util.VerifyCode;
import com.cj.user.service.UserService;
import com.cj.system.utils.sms.SmsUtil;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "用户端")
public class UserController {

    @Autowired
    private UserService userService;
/*    @Autowired
    private DefaultKaptcha producer;*/
    Gson gson = new Gson();
    /**
     * =========================================用户端 维护===========================================================
     */

    /**
     * 添加用户账号
     */
    @ApiOperation("新增用户")
    @Log(name = "用户端 ==> 新增用户")
    @PostMapping("/addUser")
    public Map addUser(@ApiParam(name = "user",value = "用户信息",required = true)
                           @RequestBody User user){

        ApiResult apiResult = userService.addUser(user);

        return apiResult.toMap();

    }



    /**
     * =======================================登录、注销=============================================
     */

    /**
     * 登录
     */
    @ApiOperation("登录")
    @Log(name = "用户 ==> 登录")
    @PostMapping("/login")
    public Map<String, Object> login(@ApiParam(name = "user",value = "userName=用户名，userPass=密码",required = true)
                                         @RequestBody User user){
        ApiResult apiResult = userService.login(user);
        return apiResult.toMap();
    }


    /**
     * 注销
     */
    @ApiOperation("注销")
    @Log(name = "用户 ==> 注销")
    @GetMapping("/logout")
    public Map<String, Object> logout(HttpSession session){

        ApiResult apiResult = userService.logout(session);
        return apiResult.toMap();
    }

    /**
     * ========================================用户 维护===================================================
     */

    /**
     * 用户自己修改密码
     */
    @ApiOperation("修改密码")
    @Log(name = "用户 ==> 修改密码")
    @PutMapping("/updateUserPass")
    public Map updateUserPass(@ApiParam(name = "json",value = "oldUserPass=原密码，newUserPass=新密码")
                                  @RequestBody String json,
                              HttpSession session){
        Map map = gson.fromJson(json,Map.class);

        ApiResult apiResult = userService.updateUserPass(map,session);

        return apiResult.toMap();
    }

    /**获取注册验证码
     * @return
     */
    @ApiOperation("获取验证码")
    @Log(name = "用户 ==> 获取注册验证码")
    @PostMapping("/getVerifyCode")
    public Map getVerifyCode(@ApiParam(name = "json",value = "phone=手机号")
                                @RequestBody Map map)throws ClientException, InterruptedException{
        ExpiryMap<String, String> codeMap= MemoryVerifyCode.getCodeMap();
        ApiResult apiResult =null;
        String strPhone = (String) map.get("phone");
        //Map map = gson.fromJson(json,Map.class);
        //该手机号已申请验证
        if (codeMap.containsKey(strPhone)) {
            apiResult = ApiResult.FAIL;
            apiResult.setMsg("重复请求");
            return apiResult.toMap();
        }

        //生成随机验证码
        String strCode = VerifyCode.getRandomNumCode();
        //存储验证码
        Map tempMap = new LinkedHashMap();
        tempMap.put("code",strCode);
        System.out.println(tempMap.toString());
        //发送短信验证嘛
        //发短信
        SendSmsResponse response = SmsUtil.sendSms(strPhone,tempMap);

        //如果发送成功
        if (response != null && SmsUtil.isSendSuccess(response)) {
            apiResult = ApiResult.CODE_200;
            apiResult.setMsg("验证码发送成功");
            codeMap.put(strPhone, strCode);
        } else {
            apiResult = ApiResult.FAIL;
            apiResult.setMsg("验证码发送失败");
        }

        return apiResult.toMap();
    }

    /**用户注册
     * @param registerInfo
     * @return
     */
    @ApiOperation("用户注册")
    @Log(name = "用户 ==> 用户注册")
    @GetMapping("/registerUser")
    public  Map registerUser(@ApiParam(name = "json",value = "用户信息,code=验证码信息",required = true) @RequestBody RegisterInfo registerInfo){
        //返回结果
        ApiResult apiResult=null;
        //存储的验证信息
        ExpiryMap<String, String> codeMap= MemoryVerifyCode.getCodeMap();
        User user = new User(registerInfo);
        String phone =user.getPhoneNumber();
        String code =registerInfo.getCode();
        System.out.println(phone);
        //该用户的验证码
        String verifyCode=null;
        if (codeMap.containsKey(phone)) {
            verifyCode = codeMap.get(phone);

        } else {
            apiResult=ApiResult.REGISTER_FAIL;
            apiResult.setMsg("请从新获取验证码");
            return apiResult.toMap();
        }

        if (code != null && code.trim().length() > 0 && code.trim().equals(verifyCode)) {
            apiResult = userService.addUser(user);
            //清除mapcode中code
            codeMap.remove(phone);
           // apiResult = ApiResult.REGISTER_SUCCESS;

        } else {
            apiResult = ApiResult.REGISTER_FAIL;
        }
        return apiResult.toMap();
    }

    /**获取用户信息
     * @return
     */
    @ApiOperation("获取用户信息")
    @Log(name = "用户==>获取用户信息")
    @GetMapping("/getUserInfo")
    public Map getUserInfo(HttpSession session){
        //取userid
        Integer userId = (Integer) session.getAttribute("id");
        //查询用户基本信息
        ApiResult apiResult = userService.getUserInfo(userId);
        return apiResult.toMap();

    }

    /**获取用户验证码
     * @return
     */
/*    @ApiOperation("修改用户信息")
    @Log(name = "用户==》修改用户信息")
    @PutMapping("/updateUserInfo")
   public Map updateUserInfo(@ApiParam(name = "json",value = "用户基本信息") @RequestBody UserInfo info){

   }*/
    @ApiOperation("用户==》》获取用户验证码图片")
    @Log(name = "用户==》》获取用户验证码图片")
    @GetMapping("/getUserVerifCodeImage")
    public Map getUserVerifCodeImage () throws IOException {
        Properties properties = new Properties();
        properties.put("kaptcha.border", "no");
        properties.put("kaptcha.textproducer.font.color", "black");
        properties.put("kaptcha.textproducer.char.space", "10");
        properties.put("kaptcha.textproducer.char.length", "4");
        properties.put("kaptcha.image.height", "34");
        properties.put("kaptcha.textproducer.font.size", "25");

        properties.put("kaptcha.noise.impl", "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        DefaultKaptcha producer = new DefaultKaptcha();
        producer.setConfig(config);


        // 生成文字验证码

        String code = producer.createText();
        // 生成图片验证码
        ByteArrayOutputStream outputStream = null;
        //生成字节流
        BufferedImage image = producer.createImage(code);

        outputStream = new ByteArrayOutputStream();
        //outputStream.write("data:image/png;base64,".getBytes());
        ImageIO.write(image, "png", outputStream);
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        String base64image=encoder.encode(outputStream.toByteArray());
        //解决编码后自动添加/r/n问题
        base64image=base64image.replaceAll("\r|\n", "");
        //添加base前缀
        base64image="data:image/png;base64,"+base64image;



        // 生成captcha的token
        String token = JwtUtil.getVerifyCodetoken(code);

        //Map<String, Object> map = captchaService.createToken(text);
        Map<String, Object> map = new HashMap<>();
        map.put("token", token);
        map.put("img",base64image);
        ApiResult apiResult =ApiResult.SUCCESS;

        apiResult.setData(map);


        return map;
    }

    /** 校验验证码
     * @return
     */
    @ApiOperation("用户=>校验验证码")
    @Log(name = "用户=>校验验证码")
    @PostMapping("/isVerityCode")
    public Map<String,Object> isVerityCode(@ApiParam(name = "code",value = "code=?")  String code, HttpServletRequest request){
        ApiResult apiResult =null;
        String token = (String) request.getHeader("token");
        String userVerifyCode = code;
        Claims claims = JwtUtil.getVerifyCodeClaims(token);
        //token解析成功
        if (claims != null) {
            String validCode = (String) claims.get("code");
            if (validCode.equalsIgnoreCase(userVerifyCode))
                apiResult = ApiResult.SUCCESS;
            else {
                apiResult = ApiResult.FAIL;
                apiResult.setMsg("验证码有误，请从新输入");
            }
        } else {//验证码过期
            apiResult=ApiResult.FAIL;
            apiResult.setMsg("验证码过期，请从新获取");
        }
        return apiResult.toMap();
    }
}
