package org.khr.demo.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import org.khr.demo.dao.entity.SysPermission;
import org.khr.demo.dao.mapper.SysPermissionMapper;
import org.khr.demo.service.SysPermissionService;
import org.springframework.stereotype.Service;

/**
 *  服务层实现。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
@Service
public class SysPermissionServiceImpl extends ServiceImpl<SysPermissionMapper, SysPermission>  implements SysPermissionService{

}
