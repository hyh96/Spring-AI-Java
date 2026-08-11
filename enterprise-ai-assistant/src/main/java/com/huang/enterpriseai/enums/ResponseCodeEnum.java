package com.huang.enterpriseai.enums;

/**
 * @Author: huang
 * @Description: TODO
 * @DateTime: 2026/8/10 16:55
 **/
public enum ResponseCodeEnum {

    RESP_200(200, "操作成功"),
    RESP_400(400, "请求参数错误"),
    RESP_401(401, "未登录或登录已失效"),
    RESP_403(403, "当前权限不足"),
    RESP_404(404, "未查询到数据"),
    RESP_409(409, "数据冲突"),
    RESP_500(500, "系统异常");

    public final Integer code;
    public final String message;

    ResponseCodeEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

}
