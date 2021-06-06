package com.xfu.entity;

import lombok.Data;

@Data
public class ProductDetails {

    private  int p_id;//商品ID
    private String pname;//商品名称
    private double price;//价格
    private  String unit;//单位
    private  int stock;//库存量
    private  String url;//图片路径
    private int sequence;//排序序号
}
