package com.xfu.mapper;

import com.xfu.entity.Category;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICategoryMapper {

    @Select("select * from tb_category  ORDER BY  RAND() LIMIT 4")
    List<Category> findAllCategory();
}
