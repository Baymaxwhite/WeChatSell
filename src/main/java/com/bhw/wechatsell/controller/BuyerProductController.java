package com.bhw.wechatsell.controller;

import com.bhw.wechatsell.util.ResultVOUtil;
import com.bhw.wechatsell.VO.ProductInfoVO;
import com.bhw.wechatsell.VO.ProductVO;
import com.bhw.wechatsell.VO.ResultVO;
import com.bhw.wechatsell.entity.ProductCategory;
import com.bhw.wechatsell.entity.ProductInfo;
import com.bhw.wechatsell.service.ProductCategoryService;
import com.bhw.wechatsell.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    @GetMapping("/list")
//    @Cacheable(cacheNames = "product" , key = "#sellerId" ,condition = "#sellerId.length() > 3" ,unless = "#result.getCode() != 0")//condition id长度；因为是unless  所以result.getCode 不等于 0
    public ResultVO list(@RequestParam("sellerId") String sellerId){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList=productService.findUpAll();
        //2.查询类目(一次性查询)
//        List<Integer> categoryTypeList=new ArrayList<>();
        //传统方法
//        for (ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType());
//        }
        //精简方法(java8，lambda)
        List<Integer> categoryTypeList=productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList=productCategoryService.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装
        List<ProductVO> productVOList=new ArrayList<>();
        for (ProductCategory productCategory: productCategoryList){
            ProductVO productVO=new ProductVO();
            productVO.setCategorytype(productCategory.getCategoryType());
            productVO.setCategoryname(productCategory.getCategoryName());


            List<ProductInfoVO> productInfoVOList=new ArrayList<>();
            for (ProductInfo productInfo: productInfoList){
                if (productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO=new ProductInfoVO();
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }

//        ResultVO resultVO=new ResultVO();
//        ProductVO productVO=new ProductVO();
//        ProductInfoVO productInfoVO=new ProductInfoVO();
//
//        productVO.setProductInfoVOList(Arrays.asList(productInfoVO));
//        resultVO.setData(Arrays.asList(productInfoVO));
//        resultVO.setData(productVOList);
//        resultVO.setCode(0);
//        resultVO.setMsg("成功");

//        resultVO.setData(productVO);
        return ResultVOUtil.success(productVOList);
    }
}
