package com.xfu.entity;

import lombok.Data;

@Data
public class Category {
    private  int id;
    private  String name;//类别名称
    private  String img_url;//商品类别图片路径
    private String extend;//备注
}
