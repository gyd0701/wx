package com.xfu.mapper;

import com.xfu.entity.WXUser;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface IWXUserMapper {

    @Insert(value = "insert into tb_user(openid,nickname,gender) values( #{user.openid},#{user.nickname},#{user.gender})" )
    public void saveWXUser(@Param("user") WXUser user);

    @Select("select id from tb_user where openid=#{openid} ")
    int finduserid(@Param("openid") String openid);

}
