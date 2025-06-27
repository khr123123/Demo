package org.khr.shardings.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author KK
 * @create 2025-06-26-17:15
 */
@Data
public class OrderVO {

    private String orderNo;
    private BigDecimal amount;
}
