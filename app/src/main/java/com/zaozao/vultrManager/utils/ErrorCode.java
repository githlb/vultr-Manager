package com.zaozao.vultrManager.utils;

/**
 * Created by sean on 16/9/30.
 */
public class ErrorCode {
    public static final int CODE_SUCCESS = 200;
    public static final int CODE_INVALID_API = 400;
    public static final int CODE_INVALID_KEY = 403;
    public static final int CODE_INVALID_HTTP_METHOD = 405;
    public static final int CODE_REQUEST_FAIL = 412;
    public static final int CODE_INTERNAL_SERVER_ERROR = 500;
    public static final int CODE_RATE_LIMIT = 503;
    public static final int CODE_CONNECT_FAIL = 9999;

    public static String getMsgByCode(int code) {
        String str = null;
        switch (code) {
            case CODE_SUCCESS:
                str = "操作成功";
                break;
            case CODE_INVALID_API:
                str = "API地址错误，请检查";
                break;
            case CODE_INVALID_KEY:
                str = "key错误，请检查";
                break;
            case CODE_INTERNAL_SERVER_ERROR:
                str = "内部服务错误";
                break;
            case CODE_REQUEST_FAIL:
                str = "操作失败，请查看返回信息";
                break;
            case CODE_INVALID_HTTP_METHOD:
                str = "http请求方法错误";
                break;
            case CODE_RATE_LIMIT:
                str = "请求频率太快，请稍后再试";
                break;
            case CODE_CONNECT_FAIL:
                str = "服务器连接失败";
                break;
            default:
                str = "未知错误";
                break;
        }
        return str;
    }
}
