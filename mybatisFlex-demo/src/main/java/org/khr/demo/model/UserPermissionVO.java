package org.khr.demo.model;

import lombok.Data;

@Data
public class UserPermissionVO {
    private Long userId;
    private String username;
    private String roleKey;
    private String permissionKey;
    private String permissionUrl;
}
