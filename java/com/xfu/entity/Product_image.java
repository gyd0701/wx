package com.xfu.entity;

import lombok.Data;

@Data
public class Product_image {

    private int id;
    private  String url;//图片路径
    private  int product_id;//商品id
    private int sequence;//排序序号
}
