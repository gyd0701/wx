package com.xfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.xfu.entity.OrderDetails;
import com.xfu.entity.Order_info;
import com.xfu.entity.Order_infoSum;
import com.xfu.entity.Order_product;
import com.xfu.mapper.IOrderformMapper;
import com.xfu.mapper.IWXUserMapper;
import com.xfu.service.IOrderformService;
import com.xfu.util.OrderCodeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderformService implements IOrderformService {
    @Autowired
    IOrderformMapper omapper;
    @Autowired
    IWXUserMapper umapper;

    //创建订单生成订单号
    @Override
    public String createOrderform(String orderdata) {
        Order_info order_info=new Order_info();
        JSONObject object = JSON.parseObject(orderdata);
        String ordercode=OrderCodeFactory.getOrderCode(Long.parseLong(umapper.finduserid(object.getString("user_id"))+""));
        order_info.setOrder_id(ordercode);
        order_info.setStatu(10);
        order_info.setUser_id(object.getString("user_id"));
        order_info.setFreight(Integer.parseInt(object.getString("freight")));
        order_info.setProduct_price(Double.parseDouble(object.getString("product_price")));
        order_info.setTotal_count(Integer.parseInt(object.getString("total_count")));
        order_info.setTotal_price(Double.parseDouble(object.getString("total_price")));
        order_info.setAdd_id(Integer.parseInt(object.getString("add_id")));
        omapper.createOrderform(order_info);
        //订单商品
         for (Object o : object.getJSONArray("productlist")) {
                    JSONObject json=(JSONObject) JSONObject.toJSON(o);
                    Order_product order_product=new Order_product();
                    order_product.setOrder_id(ordercode);
                    order_product.setProduct_id(Integer.parseInt(json.getString("id")));
                    order_product.setCount(Integer.parseInt(json.getString("quantity")));
                   omapper.insertorderproduct(order_product);
        }
        return ordercode;
    }

    @Override
    public List findWaitPayList(String userid,int status) {
        List<Order_info> orderlist=omapper.findOrderList(userid,status);
        JSONArray array=new JSONArray();
        for (Object o:orderlist){
            JSONObject object=(JSONObject) JSONObject.toJSON(o);
            JSONObject jsonObject=new JSONObject();
            JSONArray productarry=new JSONArray();
            List<OrderDetails> plist=omapper.findOrderProduct(object.getString("order_id"));
            for (Object p:plist ){
                JSONObject productlist=new JSONObject();
                JSONObject pobject=(JSONObject) JSONObject.toJSON(p);
                productlist.put("price", pobject.getString("price"));
                productlist.put("url", pobject.getString("url"));
                productlist.put("count",pobject.getString("count"));
                productlist.put("pname", pobject.getString("pname"));
                productarry.add(productlist);
            }
            jsonObject.put("productlist", productarry);
            jsonObject.put("order_id",object.getString("order_id"));
            jsonObject.put("freight",object.getString("freight"));
            jsonObject.put("total_price", object.getString("total_price"));
            jsonObject.put("total_count", object.getString("total_count"));
            jsonObject.put("create_time", object.getString("create_time"));
            array.add(jsonObject);
        }
        return array;
    }

    @Override
    public void upstate(String orderid, int status) {
        omapper.upstate(orderid,status);
    }

    @Override
    public int getDeptCount() {
        return omapper.countDepts();
    }

    @Override
    public List<Order_infoSum> getOrderInfoSum(Integer offset, Integer limit) {
        return omapper.selectOrderInfoSum(offset, limit);
    }
}
