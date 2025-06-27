package org.khr.shardings.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.khr.shardings.domain.User;
import org.khr.shardings.service.UserService;
import org.khr.shardings.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author 3031208
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-06-26 12:02:41
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




