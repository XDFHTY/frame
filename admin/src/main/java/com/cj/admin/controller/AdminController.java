package com.cj.admin.controller;


import com.cj.admin.service.AdminService;
import com.cj.core.aop.Log;
import com.cj.common.entity.Admin;
import com.cj.common.utils.entity.ApiResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@Api(tags = "管理端")
public class AdminController {
     Long id=null;

    @Autowired
    private AdminService adminService;


    /**
     * ============================================管理员登录、注销====================================================
     */

    /**
     * 登录
     */
    @ApiOperation("管理员登录")
    @PostMapping("/ifLogin")
    @Log(name = "管理端 ==> 管理员登录")
    public Map ifLogin(@ApiParam(name = "admin",value = "用户登录,用户名、密码")
                       @RequestBody Admin admin){

        ApiResult apiResult = adminService.ifLogin(admin);


        return apiResult.toMap();


    }

    @ApiOperation("注销")
    @GetMapping("/ifLogout")
    @Log(name = "管理端 ==> 注销")
    public Map ifLogout(HttpSession session){
        ApiResult apiResult = null;
        int i = adminService.ifLogout(session);
        if(i == 0 ){
            ApiResult.FAIL.setMsg("注销失败");
            apiResult = ApiResult.FAIL;
        }else {
            apiResult = ApiResult.SUCCESS;
        }

        return apiResult.toMap();
    }


    /**
     * ==============================================管理员账号维护===========================================================
     */

    /**
     * 新增账号
     */

    @ApiOperation("添加管理员账号")
    @PostMapping("/addAdmin")
    @Log(name = "管理端 ==> 添加管理员账号")
    public Map addAdmin(@ApiParam(name = "admin",value = "adminName=账号、adminPass=密码,adminType=账号类型(1=系统管理员)",required = true)
                                  @RequestBody Admin admin){

        ApiResult apiResult = adminService.addAdmin(admin);
        return apiResult.toMap();
    }

    /**
     * 修改密码，不校验原密码
     */
    @ApiOperation("修改密码，不校验原密码")
    @PutMapping("/updateAdmin")
    @Log(name = "管理端 ==> 修改密码，不校验原密码")
    public Map updateAdmin(@ApiParam(name = "admin",value = "账号、id、新密码",required = true)
            @RequestBody Admin admin){
        int i = adminService.updateAdmin(admin);
        ApiResult apiResult = null;
        if(i == 1){
            apiResult = ApiResult.UPDATE_SUCCESS;
        }else {
            apiResult = ApiResult.UPDATE_FAIL;
        }

        return apiResult.toMap();
    }

    /**
     * 删除账号
     */
    @ApiOperation("删除账号")
    @DeleteMapping("/deleteAdmin")
    @Log(name = "管理端 ==> 删除账号")
    public Map deleteAdmins(@ApiParam(name = "adminId",value = "adminId",required = true)
            Integer adminId){

        System.out.println("===============");
        System.out.println(adminId);

        int i = adminService.delete(new Long(adminId));

        ApiResult apiResult = null;
        if(i > 0){
            ApiResult.SUCCESS.setMsg("删除成功");
            apiResult = ApiResult.SUCCESS;
        }else {
            apiResult = ApiResult.DELETE_FAIL;
        }

        return apiResult.toMap();

    }


    /**
     * 查询所有账号
     */
    @ApiOperation("查询所有账号")
    @GetMapping("/findAllAdmin")
    @Log(name = "管理端 ==> 查询所有账号")
    public Map findAllAdmin(){
        ApiResult apiResult = null;
            apiResult = ApiResult.FIND_SUCCESS;
            apiResult.setData(adminService.findAllAdmin());

        return apiResult.toMap();
    }




}
