package com.xfu.entity;

import lombok.Data;
//订单统计实体
@Data
public class Order_infoSum {

    private String order_id;
    private String name;
    private  int freight;
    private double product_price;
    private  double total_price;
    private  int total_count;
    private  String create_time;//下单时间
}
