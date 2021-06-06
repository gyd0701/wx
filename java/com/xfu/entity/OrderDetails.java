package com.xfu.entity;

import lombok.Data;

@Data
public class OrderDetails {

    private  String pname;//商品名称
    private  double price;//商品价格
    private int count;//商品数量
    private  String url;//商品图片



}
