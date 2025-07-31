package org.khr.demo.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.demo.dao.entity.SysUserRole;
import org.khr.demo.dao.mapper.SysUserRoleMapper;
import org.khr.demo.service.SysUserRoleService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole>  implements SysUserRoleService{

}
