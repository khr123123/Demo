package org.khr.shardings.controller;

import org.khr.shardings.domain.User;
import org.khr.shardings.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author KK
 * @create 2025-06-26-13:34
 */
@RestController
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @GetMapping("/test")
    public String test() {
        List<User> users = userMapper.selectList(null);
        return users.toString();
    }

}
