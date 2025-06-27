package org.khr.shardings;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.khr.shardings.domain.Order;
import org.khr.shardings.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author KK
 * @create 2025-06-26-15:29
 */
@SpringBootTest
public class SliceTestX {

    @Autowired
    private OrderMapper orderMapper;

    @Test
    void test() {
        for (int i = 0; i < 3; i++) {
            Order order = new Order();
            order.setOrderNo(String.valueOf(123));
            order.setUserId(120L);
            order.setAmount(BigDecimal.valueOf(123.12));
            orderMapper.insert(order);
        }
    }

    @Test
    void test2() {
        List<Order> orders = orderMapper.selectList(null);
        orders.forEach(System.out::println);
    }

    @Test
    void testSelectByUser() {
        List<Order> orders = orderMapper.selectList(Wrappers.<Order>lambdaQuery().eq(Order::getUserId, 120L));
        orders.forEach(System.out::println);
    }

    @Test
    void test3() {
        Order order = new Order();
        order.setOrderNo("ORDER001");
        order.setUserId(120L);
        order.setAmount(BigDecimal.valueOf(123.12));
        Order order1 = new Order();
        order1.setOrderNo("ORDER001");
        order1.setUserId(120L);
        order1.setAmount(BigDecimal.valueOf(123.12));
        Order order2 = new Order();
        order2.setOrderNo("ORDER001");
        order2.setUserId(120L);
        order2.setAmount(BigDecimal.valueOf(123.12));
        orderMapper.insert(order);
        orderMapper.insert(order1);
        orderMapper.insert(order2);
    }

}
