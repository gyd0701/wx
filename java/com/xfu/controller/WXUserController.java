package com.xfu.controller;

import com.xfu.service.IWXUserService;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/wx")
public class WXUserController {

    @Autowired
    IWXUserService service;

    @ResponseBody
    @RequestMapping("/login.do")
    public ResultData login(String code, String nickname,String sex) {

        return service.wxlogin(code, nickname,sex);
    }
}
