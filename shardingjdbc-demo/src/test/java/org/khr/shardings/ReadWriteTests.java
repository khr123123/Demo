package org.khr.shardings;

import org.junit.jupiter.api.Test;
import org.khr.shardings.domain.User;
import org.khr.shardings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
class ReadWriteTests {

    @Autowired
    private UserMapper userMapper;

    // 1. 读写分离测试
    @Test
    void contextLoads() {
//        User user = new User();
//        user.setUserName("test");
//        int insert = userMapper.insert(user);
//        System.out.println("insert = " + insert);
        List<User> users = userMapper.selectList(null);
        System.out.println("users = " + users);
    }

    // 2. 事务中的读写测试
    //事务中 读写分离失效，为了保证事务跨库，shadings采用 读写都在主库进行
    @Transactional
    @Test
    void testTX() {
        User user = new User();
        user.setUserName("testTX");
        int insert = userMapper.insert(user);
        List<User> users = userMapper.selectList(null);
    }

    // 3，负载均衡测试  roundRobin
    @Test
    void testLoadBalancer() {
        // 同一个进程中 查询两次
        List<User> users = userMapper.selectList(null);
        System.out.println("users = " + users); // Actual SQL: slave1 ::: SELECT  id,userName  FROM user
        users = userMapper.selectList(null);
        System.out.println("users2 = " + users);//Actual SQL: slave2 ::: SELECT  id,userName  FROM user
        users = userMapper.selectList(null);
        System.out.println("users2 = " + users);//Actual SQL: slave1 ::: SELECT  id,userName  FROM user
        users = userMapper.selectList(null);
        System.out.println("users2 = " + users);//Actual SQL: slave2 ::: SELECT  id,userName  FROM user
    }

}