package com.bhw.wechatsell.service.impl;

import com.bhw.wechatsell.entity.SellerInfo;
import com.bhw.wechatsell.repository.SellerInfoRepository;
import com.bhw.wechatsell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
