package org.khr.shardings.mapper;

import org.apache.ibatis.annotations.Select;
import org.khr.shardings.domain.Order;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.khr.shardings.domain.OrderVO;

import java.util.List;

/**
* @author 3031208
* @description 针对表【t_order】的数据库操作Mapper
* @createDate 2025-06-26 14:56:20
* @Entity org.khr.shardings.domain.Order
*/
public interface OrderMapper extends BaseMapper<Order> {

    @Select({
        "SELECT o.order_no  AS orderNo, SUM(i.price * i.count) AS amount ",
        "FROM t_order o JOIN t_order_item i ON o.order_no = i.order_no ",
        "GROUP BY o.order_no"
    })
    List<OrderVO> getOrderAmount();


}




