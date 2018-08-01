package com.cj.common.utils.entity;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public enum ApiResult {


    //1000以内是系统错误，
    //100-511为http 状态码
    CODE_200(200, "操作成功！！"),
    CODE_401(401, "未授权"),
    CODE_400(400, "请求错误"),
    CODE_403(403, "服务器拒绝请求"),
    CODE_404(404, "资源不存在"),
    CODE_405(405, "请求方式或参数有误"),
    CODE_409(409, "冲突"),
    CODE_500(500, "服务器内部错误"),
    // --- 5xx Server Error ---

    // --- 8xx common error ---



    //1000 为通用失败
    FAIL(1000,"处理失败"),
    //1001 为通用成功
    SUCCESS(1001,"处理成功"),

    NO_know(1010,"无法识别"),
    SUCCESS_know(1011,"成功解析数据"),
    NO_data(1020,"没有数据"),


    //1100-1199 为操作信息
    ADD_FAIL(1100,"新增失败"),
    ADD_SUCCESS(1101,"新增成功"),
    account_repeat(1102,"用户名重复"),

    DELETE_FAIL(1110,"删除失败"),
    DELETE_SUCCESS(1111,"删除成功"),

    UPDATE_FAIL(1120,"修改失败"),
    UPDATE_SUCCESS(1121,"修改成功"),
    userPass_error(1122,"旧密码错误"),

    FIND_FAIL(1130,"查询失败"),
    FIND_SUCCESS(1131,"查询成功"),

    //1200-1299 为账号信息
    account_not_login(1200,"没有登录"),
    LOGIN_SUCCESS(1201,"登录成功"),
    LOGOUT_SUCCESS(1202,"注销成功"),
    REGISTER_SUCCESS(1203, "注册成功"),
    REGISTER_FAIL(1204, "注册失败"),
    account_repeat_login(1210,"该账号已在其他设备登录"),
    token_invalid(1220,"令牌失效"),
    account_not_find(1230,"用户名或密码错误或账号已过期"),



    ;


    /*两个属性*/

    private int code;
    private String msg;
    /**
     * 返回数据
     */
    private Object data;


    /**
     * 其他
     */
    protected Object params;
    /*构造函数*/
    private ApiResult(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
    /*属性的setter和getter*/
    public int getCode() {
        return this.code;
    }
    public String getMsg() {
        return this.msg;
    }

    public Object getData() {
        return data;
    }
    public Object getParams() {
        return params;
    }


    public void setCode(int code) {
        this.code = code;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(Object data) {
        this.data = data;
    }
    public void setParams(Object params) {
        this.params = params;
    }

    public Map<String, Object> toMap() {
        return ImmutableMap.<String, Object>builder()
                .put("code", code)
                .put("msg", msg)
                .put("data", data==null?"":data)
                .put("params", params==null?"":params)
                .build();
    }


}
