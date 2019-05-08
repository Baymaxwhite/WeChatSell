package com.bhw.wechatsell.enums;

import lombok.Getter;

@Getter
public enum  ProductStatusEnum implements CodeEnum{

    UP((byte)0,"在架"),
    DOWN((byte)1,"下架")
    ;
    private Byte code;
    private String message;
    ProductStatusEnum(Byte code,String message){
        this.code=code;
        this.message=message;
    }

//    private Byte pushs;
//    private String pulls;
//    ProductStatusEnum(Byte pushs,String pulls){
//        this.pushs=pushs;
//        this.pulls=pulls;
//    }
}
