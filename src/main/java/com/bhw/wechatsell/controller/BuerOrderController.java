package com.bhw.wechatsell.controller;


import com.bhw.wechatsell.VO.ResultVO;
import com.bhw.wechatsell.converter.OrderForm2OrderDTOConverter;
import com.bhw.wechatsell.dto.OrderDTO;
import com.bhw.wechatsell.enums.ResultEnum;
import com.bhw.wechatsell.exception.SellException;
import com.bhw.wechatsell.form.OrderForm;
import com.bhw.wechatsell.service.BuyerService;
import com.bhw.wechatsell.service.OrderService;
import com.bhw.wechatsell.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    //创建订单
    @PostMapping(value="/create")
    public ResultVO<Map<String, String>>  create(@Valid OrderForm orderForm,
                                                 BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【创建订单】参数不正确，orderForm={}",orderForm);
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }

        OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【创建订单】 购物不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO createResult = orderService.create(orderDTO);

        Map<String, String> map=new HashMap<>();
        map.put("orderId",createResult.getOrderId());

        return ResultVOUtil.success(map);
    }

    //订单列表
    @GetMapping("/list")
//    @RequestMapping(value="/list",method= RequestMethod.GET)
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page",defaultValue = "0") Integer page,
                                         @RequestParam(value = "size",defaultValue = "10") Integer size){
        log.debug(openid);
        if (StringUtils.isEmpty(openid)){
            log.error("【订单查询列表】 openid为空");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        PageRequest request= new PageRequest(page , size);
        Page<OrderDTO> orderDTOPage=orderService.findList(openid,request);

        return ResultVOUtil.success(orderDTOPage.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){
//        OrderDTO orderDTO = orderService.findOne(orderId);
//        if (!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
//
//        }
        OrderDTO orderDTO = buyerService.findOrderOne(openid,orderId);
        return  ResultVOUtil.success(orderDTO);
    }
    //取消订单
    @PostMapping("cancel")
    public ResultVO<OrderDTO> cancel(@RequestParam("openid") String openid,
                               @RequestParam("orderId") String orderId){
//        OrderDTO orderDTO= orderService.findOne(orderId);
//        orderService.cancel(orderDTO);
        buyerService.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }
}
