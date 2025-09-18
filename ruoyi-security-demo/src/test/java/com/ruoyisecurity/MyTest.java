package com.ruoyisecurity;

import cn.hutool.json.JSONUtil;
import com.ruoyisecurity.domain.SysMenu;
import com.ruoyisecurity.service.SysUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@SpringBootTest
public class MyTest {
    @Autowired
    private SysUserService sysUserService;  // 用户服务类，用于处理与用户相关的业务逻辑。

    @Test
    void TestTree1() {
        List<SysMenu> menuList = sysUserService.getUserMenuList(1L);
        // 对原始菜单实现排序
        menuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        // 过滤掉类型为 "F" 的菜单
        menuList = menuList.stream().filter(sysMenu -> !Objects.equals(sysMenu.getMenuType(), "F")).toList();
        // 创建一个 Map 来存储所有菜单
        Map<Long, SysMenu> menuMap = menuList.stream()    // Function.identity() 引用List中的同一个对象
                .collect(Collectors.toMap(SysMenu::getId, Function.identity()));
        // 挂载子菜单
        List<SysMenu> sysMenus = new ArrayList<>();
        for (SysMenu menu : menuList) {
            if (menu.getParentId() == 0) {
                // 添加父节点
                sysMenus.add(menu);
            } else {
                // 将子节点挂到父节点上
                SysMenu parentMenu = menuMap.get(menu.getParentId());
                if (parentMenu != null) {
                    // 这里在map里面拿到的 和 刚刚加进新List的是同一个对象
                    parentMenu.getChildren().add(menu);
                }
            }
        }
        System.out.println(JSONUtil.parse(sysMenus));
    }

    @Test
    void TestTree2() {
        List<SysMenu> menuList = sysUserService.getUserMenuList(1L);
        // 对原始菜单实现排序
        menuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        // 过滤掉类型为 "F" 的菜单
        menuList = menuList.stream().filter(sysMenu -> !Objects.equals(sysMenu.getMenuType(), "F")).toList();
        List<SysMenu> recursion = convertLine2Tree(menuList, 0L);
        System.out.println(JSONUtil.parse(recursion));
    }

    // 递归实现
    public List<SysMenu> convertLine2Tree(List<SysMenu> menuList, Long Pid) {
        List<SysMenu> children = new ArrayList<>();
        menuList.forEach(item -> {
            if (item.getId() != null
                    && item.getParentId() != null
                    && item.getParentId().equals(Pid)) {
                item.setChildren(convertLine2Tree(menuList, item.getId()));
                children.add(item);
            }
        });
        return children;
    }


    @Test
    void 浅拷贝() {
        List<SysMenu> menuList = sysUserService.getUserMenuList(1L);
        // 对原始菜单实现排序
        menuList.sort(Comparator.comparing(SysMenu::getOrderNum));
        // 过滤掉类型为 "F" 的菜单
        menuList = menuList.stream().filter(sysMenu -> !Objects.equals(sysMenu.getMenuType(), "F")).toList();
        // 创建一个 Map 来存储所有菜单
        Map<Long, SysMenu> menuMap = menuList.stream()    // Function.identity() 引用List中的同一个对象
                .collect(Collectors.toMap(SysMenu::getId, Function.identity()));
        System.out.println(menuMap.get(1L).equals(menuList.get(0))); // true
    }
}
