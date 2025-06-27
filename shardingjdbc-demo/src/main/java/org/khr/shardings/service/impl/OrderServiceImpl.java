package org.khr.shardings.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.khr.shardings.domain.Order;
import org.khr.shardings.service.OrderService;
import org.khr.shardings.mapper.OrderMapper;
import org.springframework.stereotype.Service;

/**
* @author 3031208
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2025-06-26 14:56:20
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

}




