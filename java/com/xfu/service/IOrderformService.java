package com.xfu.service;

import com.xfu.entity.Order_info;
import com.xfu.entity.Order_infoSum;

import java.util.List;

public interface IOrderformService
{
    String createOrderform(String orderdata);

    List<Order_info> findWaitPayList(String userid, int status);

    void upstate(String orderid,int status);

     int getDeptCount();

     List<Order_infoSum> getOrderInfoSum(Integer offset, Integer limit);
}
