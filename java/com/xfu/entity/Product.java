package com.xfu.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Product {
    private int p_id;
    private String pname;//商品名称
    private double price;//价格
    private  String unit;//单位
    private  int stock;//库存量
    private Date create_time;//创建时间
    private  Date update_time;//更新时间
    private  int category_id;//商品类别
}
