package com.example.demo.model;


public enum RespCode {
    SUCCESS(0, "请求成功"),
    WARN(-1, "网络异常，请稍后重试"),
    UNAUTHORIZED(-2,"鉴权失败");

    private int code;
    private String msg;

    RespCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


}
