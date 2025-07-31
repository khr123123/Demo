package org.khr.demo.service;

import com.mybatisflex.core.service.IService;
import org.khr.demo.dao.entity.SysUser;
import org.khr.demo.model.UserPermissionVO;

import java.util.List;

/**
 *  服务层。
 *
 * @author KK
 * @since 2025-07-31 14:13:27
 */
public interface SysUserService extends IService<SysUser> {

    List<UserPermissionVO> showPermission(Long userId);
}
