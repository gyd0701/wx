package com.xfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfu.entity.WXUser_address;
import com.xfu.mapper.IWXUser_addressMapper;
import com.xfu.service.IWXUser_addressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WXUser_addressService implements IWXUser_addressService {
    @Autowired
    IWXUser_addressMapper mapper;

    @Override
    public List<WXUser_address> findAllAdd(String userid) {
        return mapper.findAllAddress(userid);
    }

    @Override
    public WXUser_address defaultadd(String userid) {
        return mapper.defaultadd(userid);
    }

    @Override
    public void alterAdd(String alteradddata,String addid){
            WXUser_address address=new WXUser_address();
            address.setId(Integer.parseInt(addid));
            JSONObject object = JSON.parseObject(alteradddata);
            address.setName(object.getString("name"));
            address.setDetail(object.getString("detail"));
            address.setMobile(object.getString("mobile"));
            address.setIsdefault(Integer.parseInt(object.getString("isdefault")));
            address.setRemark(object.getString("remark"));
            mapper.alterdefault(object.getString("userid"));
            mapper.alterAdd(address);
    }

    @Override
    public void delAdd(String addid) {
        mapper.delAdd(Integer.parseInt(addid));
    }

    @Override
    public void insertAdd(String insertadd) {
        WXUser_address add=new WXUser_address();
        JSONObject object = JSON.parseObject(insertadd);
        add.setUser_id(object.getString("userid"));
        add.setName(object.getString("name"));
        add.setDetail(object.getString("detail"));
        add.setMobile(object.getString("mobile"));
        add.setIsdefault(Integer.parseInt(object.getString("isdefault")));
        add.setRemark(object.getString("remark"));
        mapper.alterdefault(object.getString("userid"));
        mapper.insertAdd(add);
    }
}
