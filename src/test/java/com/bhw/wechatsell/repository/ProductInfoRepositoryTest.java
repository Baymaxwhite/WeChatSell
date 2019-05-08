package com.bhw.wechatsell.repository;

import com.bhw.wechatsell.entity.ProductInfo;
import com.bhw.wechatsell.enums.ProductStatusEnum;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo= new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("红糖粥");
        productInfo.setProductPrice(new BigDecimal(3.2));
        productInfo.setProductStock(100);
        productInfo.setProductDescription("热卖的粥");
        productInfo.setProductIcon("http://XXXXXXX.jpg");
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        productInfo.setCategoryType(2);

        ProductInfo result=productInfoRepository.save(productInfo);
        Assert.assertNotNull(result);
    }


    @Test
    public void findByProductStatus() throws Exception{

        List<ProductInfo> productInfoList= productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
        Assert.assertNotEquals(0,productInfoList.size());
    }
}