package org.khr.shardings;

import org.junit.jupiter.api.Test;
import org.khr.shardings.domain.Order;
import org.khr.shardings.domain.User;
import org.khr.shardings.mapper.OrderMapper;
import org.khr.shardings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author KK
 * @create 2025-06-26-15:06
 */
@SpringBootTest
public class SliceTest {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private OrderMapper orderMapper;

    //垂直分片测试
    @Test
    public void test() {
        User user = new User();
        user.setUserName("SliceTest");
        int insert = userMapper.insert(user);
        System.out.println(insert);
        Order order = new Order();
        order.setOrderNo(String.valueOf(123));
        order.setUserId(123L);
        order.setAmount(BigDecimal.valueOf(123.12));
        int insert1 = orderMapper.insert(order);
        System.out.println(insert1);
    }

    @Test
    public void test2() {
        List<User> users = userMapper.selectList(null);
        System.out.println("users = " + users);
        List<Order> orders = orderMapper.selectList(null);
        System.out.println("orders = " + orders);
    }

}
