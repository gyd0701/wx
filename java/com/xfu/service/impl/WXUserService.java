package com.xfu.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xfu.entity.WXUser;
import com.xfu.mapper.IWXUserMapper;
import com.xfu.service.IWXUserService;
import com.xfu.util.Content;
import com.xfu.util.HttpUtils;
import com.xfu.util.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WXUserService implements IWXUserService{

    @Autowired
    IWXUserMapper mapper;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    @Override
    public ResultData wxlogin(String code,String nickname,String sex) {
        ResultData resultData;
        String result=null;
        try {
            resultData = new ResultData();
            result = HttpUtils.doHttpGet(Content.AUTHCURL + code + Content.AUTHTAIL);
            WXUser user = new WXUser();
            JSONObject object = JSON.parseObject(result);
            user.setNickname(nickname);
            user.setOpenid(object.getString("openid"));
            user.setGender(sex);
            mapper.saveWXUser(user);
            resultData.setStatus(1);
            resultData.setData(object.getString("openid"));
            return resultData;
        } catch (Exception e) {
            resultData = new ResultData();
            resultData.setStatus(1);
            JSONObject object=JSONObject.parseObject(result);
            resultData.setData(object.getString("openid"));
        }
        return resultData;
    }
}
