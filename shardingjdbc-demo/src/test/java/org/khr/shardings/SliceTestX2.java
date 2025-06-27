package org.khr.shardings;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.junit.jupiter.api.Test;
import org.khr.shardings.domain.Order;
import org.khr.shardings.domain.OrderItem;
import org.khr.shardings.domain.OrderVO;
import org.khr.shardings.mapper.OrderItemMapper;
import org.khr.shardings.mapper.OrderMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author KK
 * @create 2025-06-26-16:59
 */
@SpringBootTest
public class SliceTestX2 {

    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    @Test
    public void sliceTest2() {
        List<OrderVO> orderAmount = orderMapper.getOrderAmount();
        orderAmount.forEach(System.out::println);
    }
    @Test
    public void sliceTest() {
//        List<Order> orders = orderMapper.selectList(null);
//        List<OrderVO> collect = orders.stream().map(this::getOrderVO).collect(Collectors.toList());
//        collect.forEach(System.out::println);
        Order order = orderMapper.selectById(1145043395958276096L);
        System.out.println(getOrderVO(order));
    }

    protected OrderVO getOrderVO(Order order) {
        OrderVO orderVO = new OrderVO();
        orderVO.setOrderNo(order.getOrderNo());
        BigDecimal totalAmount = orderItemMapper.selectList(
                Wrappers.<OrderItem>lambdaQuery().eq(OrderItem::getOrderNo, order.getOrderNo()))
            .stream()
            .map(i -> {
                BigDecimal price = i.getPrice().setScale(2, BigDecimal.ROUND_HALF_UP);
                Integer count = i.getCount() != null ? i.getCount() : 0;
                return price.multiply(BigDecimal.valueOf(count));
            })
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        orderVO.setAmount(totalAmount);
        return orderVO;
    }


    @Test
    public void test() {
        for (int i = 0; i < 5; i++) {
            Order order = new Order();
            order.setOrderNo("ORDER" + i);
            order.setUserId(100L);
            order.setAmount(BigDecimal.valueOf(100.0));
            orderMapper.insert(order);

            for (int i1 = 0; i1 < 2; i1++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ORDER" + i);
                orderItem.setUserId(100L);
                orderItem.setPrice(BigDecimal.valueOf(50.0));
                orderItem.setCount(1);
                orderItemMapper.insert(orderItem);
            }
        }

        for (int i = 5; i < 9; i++) {
            Order order = new Order();
            order.setOrderNo("ORDER" + i);
            order.setUserId(101L);
            order.setAmount(BigDecimal.valueOf(200.0));
            orderMapper.insert(order);

            for (int i1 = 0; i1 < 2; i1++) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderNo("ORDER" + i);
                orderItem.setUserId(101L);
                orderItem.setPrice(BigDecimal.valueOf(100.0));
                orderItem.setCount(1);
                orderItemMapper.insert(orderItem);
            }
        }
    }


}
