package com.example.mapper;

import com.example.order.data.OrderDto;
import com.example.order.data.OrderEntity;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderEntity toEntity(OrderDto dto){
        OrderEntity entity = new OrderEntity();

        entity.setId(dto.getId());
        entity.setTableNumber(dto.getTableNumber());
        entity.setStartDate(dto.getStartDate());
        entity.setEndDate(dto.getEndDate());
        entity.setServingCode(dto.getServingCode());
        entity.setPhoneNumbClient(dto.getPhoneNumbClient());
        entity.setClientName(dto.getClientName());
        entity.setDeposit(dto.getDeposit());
        entity.setManagerCode(dto.getManagerCode());

        return entity;
    }

    public List<OrderEntity> toEntities(Collection<OrderDto> dtos) {
        return dtos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }

    public OrderDto toOrderDto(OrderEntity entity){
        OrderDto dto = new OrderDto();

        dto.setId(entity.getId());
        dto.setTableNumber(entity.getTableNumber());
        dto.setStartDate(dto.getStartDate());
        dto.setEndDate(entity.getEndDate());
        dto.setServingCode(entity.getServingCode());
        dto.setPhoneNumbClient(entity.getPhoneNumbClient());
        dto.setClientName(entity.getClientName());
        dto.setDeposit(entity.getDeposit());
        dto.setManagerCode(entity.getManagerCode());

        return dto;
    }

    public List<OrderDto> toDtos(Collection<OrderEntity> dtos) {
        return dtos.stream()
                .map(this::toOrderDto)
                .collect(Collectors.toList());
    }
}
