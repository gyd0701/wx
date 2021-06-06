package com.xfu.entity;

import lombok.Data;

@Data
public class Order_product {

    private String order_id;
    private int product_id;
    private int count;
}
