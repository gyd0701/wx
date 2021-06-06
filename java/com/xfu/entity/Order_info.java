package com.xfu.entity;

import lombok.Data;

@Data
public class Order_info {

    private String order_id;
    private int statu;
    private String user_id;
    private  int freight;
    private double product_price;
    private  double total_price;
    private  int total_count;
    private int add_id;
    private  String create_time;//下单时间
}
