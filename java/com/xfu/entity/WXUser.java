package com.xfu.entity;

import lombok.Data;

@Data
public class WXUser {
    private int id;
    private String openid;
    private String nickname;//微信名称
    private  String create_time;//注册时间
    private  String gender;//性别  1-男 2-女
    private  String extend;//备注


}
