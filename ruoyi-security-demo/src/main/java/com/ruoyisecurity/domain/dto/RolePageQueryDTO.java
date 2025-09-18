package com.ruoyisecurity.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RolePageQueryDTO {
    // role姓名
    private String name;

    // 页码
    private long pageNum ;

    // 每页显示记录数
    private long pageSize;
}
