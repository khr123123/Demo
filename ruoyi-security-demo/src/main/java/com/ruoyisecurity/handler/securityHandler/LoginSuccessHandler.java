package com.ruoyisecurity.handler.securityHandler;

import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.domain.SysRole;
import com.ruoyisecurity.domain.SysUser;
import com.ruoyisecurity.domain.vo.Result;
import com.ruoyisecurity.service.SysMenuService;
import com.ruoyisecurity.service.SysRoleService;
import com.ruoyisecurity.service.SysUserService;
import com.ruoyisecurity.utils.JsonUtils;
import com.ruoyisecurity.utils.JwtUtil;
import com.ruoyisecurity.utils.ServletUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 登录成功处理器，用于处理用户登录成功后的逻辑。
 */
@Component
@RequiredArgsConstructor
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtUtil jwtUtil; // JWT 工具类，用于生成和解析 JWT 令牌。
    private final SysUserService sysUserService;  // 用户服务类，用于处理与用户相关的业务逻辑。
    private final SysMenuService sysMenuService; // 菜单服务类，用于处理与菜单相关的业务逻辑。
    private final SysRoleService sysRoleService; // 角色服务类，用于处理与角色相关的业务逻辑。

    /**
     * 处理用户登录成功后的逻辑。
     *
     * @param request  HTTP 请求对象
     * @param response HTTP 响应对象
     * @param authentication 认证信息
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 获取用户名
        String username = authentication.getName();
        // 生成 JWT 令牌
        String token = jwtUtil.createJWT(username);
        // 查询用户信息
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        // 记录最后登录时间
        sysUser.setLoginDate(new Date());
        sysUserService.updateById(sysUser);
        // 设置用户的角色列表
        sysUser.setRoles(sysRoleService.getUserRolesList(sysUser.getId()).stream()
                .map(SysRole::getName)
                .collect(Collectors.joining(",")));
        // 获取用户的权限菜单列表
        List<SysMenu> menuList = sysUserService.getUserMenuList(sysUser.getId());
        // 递归构建菜单树
        List<SysMenu> sysMenuTree = sysMenuService.buildTreeMenu(menuList);
        // 将结果以 JSON 格式返回给客户端
        ServletUtils.renderString(response, JsonUtils.toString(Result.success(Map.of(
                "authorization", token,
                "menus", sysMenuTree,
                "userInfo", sysUser
        ))));
    }

    // 递归实现  线性数据 转 树形数据
    // public List<SysMenu> convertLine2Tree(List<SysMenu> menuList, Long Pid) {
    //     List<SysMenu> children = new ArrayList<>();
    //     menuList.forEach(item -> {
    //         if (item.getId() != null
    //                 && item.getParentId() != null
    //                 && item.getParentId().equals(Pid)) {
    //             item.setChildren(convertLine2Tree(menuList, item.getId()));
    //             children.add(item);
    //         }
    //     });
    //     return children;
    // }
}
