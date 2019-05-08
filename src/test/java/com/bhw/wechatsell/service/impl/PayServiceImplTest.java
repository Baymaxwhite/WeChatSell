package com.bhw.wechatsell.service.impl;

import com.bhw.wechatsell.dto.OrderDTO;
import com.bhw.wechatsell.service.OrderService;
import com.bhw.wechatsell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private PayService payService;

    @Test
    public void create() {
        OrderDTO orderDTO =orderService.findOne("15565216568287678006");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("15565216568287678006");
        payService.refund(orderDTO);
    }
}