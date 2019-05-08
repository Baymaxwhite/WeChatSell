package com.bhw.wechatsell.service.impl;

import com.bhw.wechatsell.dto.CartDTO;
import com.bhw.wechatsell.entity.ProductInfo;
import com.bhw.wechatsell.enums.ProductStatusEnum;
import com.bhw.wechatsell.enums.ResultEnum;
import com.bhw.wechatsell.exception.SellException;
import com.bhw.wechatsell.repository.ProductInfoRepository;
import com.bhw.wechatsell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Override
//    @Cacheable(cacheNames = "product",key = "123")
//    @Cacheable(key = "123")
    public ProductInfo findOne(String productId) {
//        return productInfoRepository.findById(productId).get();
        return productInfoRepository.findById(productId).isPresent()?productInfoRepository.findById(productId).get():null;
    }

    @Override
    public List<ProductInfo> findUpAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return productInfoRepository.findAll(pageable);
    }

    @Override
//    @CachePut(cacheNames = "product",key = "123")
//    @CachePut(key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return productInfoRepository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO:cartDTOList){
            Optional<ProductInfo> optionalT = productInfoRepository.findById(cartDTO.getProductId());
            ProductInfo productInfo =optionalT.isPresent()?optionalT.get():null;
            if (productInfo ==null ){
                throw new SellException(ResultEnum.PROTECTED_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);

            productInfoRepository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for (CartDTO cartDTO: cartDTOList){
            Optional<ProductInfo> optionalT = productInfoRepository.findById(cartDTO.getProductId());
            ProductInfo productInfo =optionalT.isPresent()?optionalT.get():null;
            if (productInfo ==null ){
                throw new SellException(ResultEnum.PROTECTED_NOT_EXIST);
            }
            Integer result=productInfo.getProductStock() - cartDTO.getProductQuantity();
            if (result<0){
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);

            productInfoRepository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId){
        Optional<ProductInfo> optionalT = productInfoRepository.findById(productId);
        ProductInfo productInfo = optionalT.isPresent()?optionalT.get():null;
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return  productInfoRepository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        Optional<ProductInfo> optionalT = productInfoRepository.findById(productId);
        ProductInfo productInfo = optionalT.isPresent()?optionalT.get():null;
        if (productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
        }
        if (productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return  productInfoRepository.save(productInfo);
    }

}
