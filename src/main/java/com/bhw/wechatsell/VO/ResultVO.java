package com.bhw.wechatsell.VO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;


/**
 * Http请求访问的最外层对象
 */
@Data
public class ResultVO <T> implements Serializable {

    private static final long serialVersionUID = 5846645492097101640L;
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 提示信息
     */
    private String msg;
    /**
     * 具体内容
     */
    private T data;
}
