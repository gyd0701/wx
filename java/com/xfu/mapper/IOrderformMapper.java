package com.xfu.mapper;

import com.xfu.entity.OrderDetails;
import com.xfu.entity.Order_info;
import com.xfu.entity.Order_infoSum;
import com.xfu.entity.Order_product;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IOrderformMapper {

    @Insert(value = "insert into tb_order_info(" +
            "order_id,statu,user_id,freight,product_price,total_price,total_count,add_id" +
            ") values(" +
            " #{order_info.order_id},#{order_info.statu},#{order_info.user_id},#{order_info.freight},#{order_info.product_price},#{order_info.total_price},#{order_info.total_count},#{order_info.add_id})" )
         void createOrderform(@Param("order_info") Order_info order_info);

    @Insert("insert into tb_order_product(order_id,product_id,count) values(#{order_product.order_id},#{order_product.product_id},#{order_product.count})")
        void insertorderproduct(@Param("order_product")Order_product order_product);

//    @Select("select e.order_id,e.freight,e.create_time,e.total_price,e.total_count,e.count,e.price,e.pname,e.url from ( " +
//            "select d.*,i.url from ( " +
//            "select c.*,p.pname,p.price from ( " +
//            "select a.*,b.product_id,b.count from tb_order_info a join tb_order_product b on a.order_id=b.order_id where a.user_id=#{user_id} "+
//            ") c " +
//            "join tb_product p on c.product_id=p.p_id"+
//            ") d " +
//            "join tb_product_image i on d.product_id=i.product_id where i.sequence=6 "+
//            ") e where e.status=10")
    @Select("select * from tb_order_info where user_id=#{user_id} and statu=#{statu}")
        List<Order_info> findOrderList(@Param("user_id") String user_id ,@Param("statu")int statu);

    @Select("select b.*,i.url from (" +
            "select a.*,p.pname,p.price  from( " +
            "select product_id,count from tb_order_product where order_id=#{order_id}) a " +
            "join tb_product p on a.product_id=p.p_id) b " +
            "join tb_product_image i on b.product_id=i.product_id where i.sequence=6 ")
    List<OrderDetails> findOrderProduct(@Param("order_id") String order_id);

    @Update("UPDATE tb_order_info SET statu = #{statu} WHERE order_id = #{order_id}")
    void upstate(@Param("order_id") String order_id ,@Param("statu")int statu);

    @Select("SELECT COUNT(*) FROM tb_order_info")
    int countDepts();

    @Select("select o.order_id,n.`name`,o.freight,product_price,o.total_price,o.total_count,o.create_time from ("+
    "select name,id from tb_user_address) n join tb_order_info o  on o.add_id=n.id  LIMIT #{offset}, #{limit}")
    List<Order_infoSum> selectOrderInfoSum(@Param("offset") Integer offset,@Param("limit") Integer limit);

}
