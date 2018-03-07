package com.example.demo.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespEntity {
    private int code;
    private String msg;
    private Object data;

    public RespEntity(RespCode respCode){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    public RespEntity(RespCode respCode,Object data){
        //this()指调用本类中的构造方法，只能存在于构造方法内
        this(respCode);
        this.data = data;
    }

    //初始化RespCode
    public void setRespCode(RespCode respCode){
        this.code = respCode.getCode();
        this.msg = respCode.getMsg();
    }

    protected boolean canEqual(Object other) {
        return other instanceof RespEntity;
    }


}
