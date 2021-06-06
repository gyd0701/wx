package com.xfu.mapper;


import com.xfu.entity.Product;
import com.xfu.entity.ProductDetails;
import com.xfu.entity.Product_image;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductMapper {
     //存商品信息
     void saveAllProduct(@Param("productList") List<Product> productList);
     //存图片
     void saveAllProduct_img(@Param("product_imgList") List<Product_image> product_imgList);
     //查询一个商品信息
     List<ProductDetails> findOneProduct(@Param("product_id") int product_id);

     //查询分类商品信息
     List<ProductDetails> findAllProduct(@Param("categoryId") int categoryId);

     //查询分类商品信息
     List<ProductDetails> findMrtjList();

     //模糊搜索
     List<ProductDetails> searchProduct(@Param("store") String store);

     //删除
     int deleteOneById(@Param("p_id") Integer p_id);


     int getProductCount();


     List<ProductDetails> selectAllProduct(@Param("offset") Integer offset,@Param("limit") Integer limit);


}
