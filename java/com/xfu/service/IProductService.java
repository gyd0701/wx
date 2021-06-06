package com.xfu.service;


import com.xfu.entity.Product;
import com.xfu.entity.ProductDetails;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;

public interface IProductService {

    //上传商品
     void analysisExcel(MultipartFile  file) throws IOException;

     //查询一件商品
     List<ProductDetails> findOneProduct(int Product_id);

    //查询分类商品
    List<ProductDetails> findAllProduct(int categoryId);

    //查询每日推荐商品
    List<ProductDetails> findMrtjList();
    //模糊搜索
    List<ProductDetails>  searchProduct(String store);

    int getProductCount();

    List<ProductDetails> findAllProduct(Integer offset, Integer limit);

    public int deletePById(Integer pId);
}
