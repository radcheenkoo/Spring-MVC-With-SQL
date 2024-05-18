package com.example.order.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;
    private int tableNumber;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private String servingCode;
    private String phoneNumbClient;
    private String clientName;
    private BigDecimal deposit;
    private String ManagerCode;
}
