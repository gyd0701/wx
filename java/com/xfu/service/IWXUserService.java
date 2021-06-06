package com.xfu.service;

import com.xfu.util.ResultData;

public interface IWXUserService {

    ResultData wxlogin(String code, String nickname,String sex);
}
