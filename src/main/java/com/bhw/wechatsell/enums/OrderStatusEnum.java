package com.bhw.wechatsell.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum implements CodeEnum {
    NEW((byte)0,"新订单"),
    FINISHED((byte)1,"完结"),
    CANCEL((byte)2,"已取消")
    ;
    private Byte code;
    private String message;
    OrderStatusEnum(Byte code,String message){
        this.code=code;
        this.message=message;
    }
}
