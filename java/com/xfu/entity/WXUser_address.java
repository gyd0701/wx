package com.xfu.entity;

import lombok.Data;

@Data
public class WXUser_address {
    private int id;
    private String name;//收货人名称
    private  String mobile;//手机号
    private  String detail;//收货地址
    private  String user_id;//用户的openID
    private  int   isdefault;//是否为默认地址
    private  String remark;//用户备注
}
