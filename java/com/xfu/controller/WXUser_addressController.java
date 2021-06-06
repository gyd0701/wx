package com.xfu.controller;

import com.xfu.service.IWXUser_addressService;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WXUser_addressController {
    @Autowired
    IWXUser_addressService service;

    @RequestMapping("/findalladd.do")
    @ResponseBody
    public ResultData findAllAddress(String userid){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.findAllAdd(userid));
        return  resultData;
    }

    @RequestMapping("/defaultadd.do")
    @ResponseBody
    public ResultData defaultadd(String userid){
        ResultData resultData=new ResultData();
        resultData.setStatus(1);
        resultData.setData(service.defaultadd(userid));
        return  resultData;
    }

    @RequestMapping("/alteradd.do")
    public  void alterAdd(String alteradddata,String addid){
       service.alterAdd(alteradddata, addid);
    }

    @RequestMapping("/deladd.do")
    public void delAdd(String addid){
        service.delAdd(addid);
    }

    @RequestMapping("/insertadd.do")
    public void insertAdd(String insert){
        service.insertAdd(insert);
    }
}
