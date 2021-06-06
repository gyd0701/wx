package com.xfu.service;

import com.xfu.entity.WXUser_address;

import java.util.List;

public interface IWXUser_addressService {
    //查询所有该用户地址
    public List<WXUser_address> findAllAdd(String userid);
    //查询默认地址
    public WXUser_address defaultadd(String userid);
    //修改地址
    public void alterAdd(String alteradddata,String addid);
    //删除地址
    public void delAdd(String addid);
    //新增地址
    public void insertAdd(String insertadd);
}
