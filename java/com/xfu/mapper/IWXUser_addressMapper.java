package com.xfu.mapper;

import com.xfu.entity.WXUser_address;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IWXUser_addressMapper {
    @Select("select * from tb_user_address where user_id=#{userid}")
    List<WXUser_address> findAllAddress(@Param("userid") String userid);

    @Select("select * from tb_user_address where user_id=#{userid} and isdefault=1")
    WXUser_address defaultadd(@Param("userid") String userid);

    @Update("update tb_user_address set "+
            "name=#{useradd.name} , mobile=#{useradd.mobile} , detail=#{useradd.detail} ,isdefault=#{useradd.isdefault}, remark=#{useradd.remark}"+
            " where id=#{useradd.id}")
    public void alterAdd(@Param("useradd") WXUser_address useradd);

    @Delete("delete from tb_user_address where id=#{addid}")
    public void delAdd(@Param("addid") int addid);

    @Insert("insert into tb_user_address"+
            "(name,mobile,detail,user_id,isdefault,remark) values "+
            "(#{add.name} , #{add.mobile} ,#{add.detail} ,#{add.user_id},#{add.isdefault}, #{add.remark})")
    public void insertAdd(@Param("add") WXUser_address add);

    @Update("update tb_user_address set isdefault=0 where (user_id=#{userid} and isdefault=1)")
    public  void alterdefault(@Param("userid")String userid);
}
