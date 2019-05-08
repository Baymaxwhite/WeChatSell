package com.bhw.wechatsell.handler;

import com.bhw.wechatsell.VO.ResultVO;
import com.bhw.wechatsell.config.ProjectUrlConfig;
import com.bhw.wechatsell.exception.ResponseBankException;
import com.bhw.wechatsell.exception.SellException;
import com.bhw.wechatsell.exception.SellerAuthorizeException;
import com.bhw.wechatsell.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://baymaxsell.natapp1.cc/wechatsell/wechat/qrAuthorize?returnUrl=http://baymaxsell.natapp1.cc/wechatsell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    public ModelAndView handlerAuthorizeException() {
        return new ModelAndView("redirect:"
                .concat(projectUrlConfig.getWechatOpenAuthorize())
                .concat("/wechatsell/wechat/qrAuthorize")
                .concat("?returnUrl=")
                .concat(projectUrlConfig.getWechatsell())
                .concat("/wechatsell/seller/login"));
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    public ResultVO handlerSellerException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

//    @ExceptionHandler(value = ResponseBankException.class)
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    public void handlerResponseBankException(){
//
//    }
}
