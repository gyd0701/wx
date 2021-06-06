package com.xfu.controller;

import com.xfu.service.ICategoryService;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    ICategoryService service;

    @RequestMapping("/findall.do")
    @ResponseBody
    public ResultData categoryfindall(){
    ResultData resultData=new ResultData();
    resultData.setStatus(1);
    resultData.setData(service.findAll());
    return resultData;
    }
}
