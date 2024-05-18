package com.example.order;


import com.example.db.DataBase;
import com.example.exception.OrderNotFoundException;
import com.example.mapper.OrderMapper;
import com.example.order.data.OrderDto;
import com.example.order.data.OrderEntity;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Setter
@Service
public class OrderService {
    private static final String CREATE_ORDER_ENTITY = "INSERT INTO orders (table_number, start_date, end_date, serving_code, phone_number_client, client_name, deposit, manager_code) VALUES(?,?,?,?,?,?,?,?)";
    private static final String SELECT_ORDER_ENTITY_BY_ID = "SELECT * FROM orders WHERE id = ?";
    private static final String UPDATE_ORDER_ENTITY_BY_ID = "UPDATE orders SET table_number = ?, start_date = ?, end_date = ?, serving_code = ?, phone_number_client = ?, client_name = ?, deposit = ?, manager_code = ? WHERE id = ?";
    private static final String DELETE_ORDER_ENTITY_BY_ID = "DELETE FROM orders WHERE id = ?";
    private static final String SELECT_ALL_ORDER = "SELECT * FROM orders";


    private Connection connection = DataBase.getInstance().getConnection();

    public void createOrder(OrderDto dto) {
        if (Objects.isNull(dto)) {
            throw new IllegalArgumentException("Dto повинна мати дані");
        }

        try {
            PreparedStatement ps = connection.prepareStatement(CREATE_ORDER_ENTITY);

            ps.setInt(1, dto.getTableNumber());
            ps.setTimestamp(2, Timestamp.valueOf(dto.getStartDate()));
            ps.setTimestamp(3, Timestamp.valueOf(dto.getEndDate()));
            ps.setString(4, dto.getServingCode());
            ps.setString(5, dto.getPhoneNumbClient());
            ps.setString(6, dto.getClientName());
            ps.setBigDecimal(7, dto.getDeposit());
            ps.setString(8, dto.getManagerCode());

            // Виконуємо запит до бази даних
            int rowsAffected = ps.executeUpdate();

            // Перевіряємо, чи були внесені зміни
            if (rowsAffected == 0) {
                throw new RuntimeException("Не вдалося створити замовлення");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public OrderEntity getOrderById(Long id){
       OrderEntity entity = new OrderEntity();

        try{
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ORDER_ENTITY_BY_ID);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.wasNull()){
                throw new OrderNotFoundException(id);
            }

            while (resultSet.next()){

                entity.setId(id);
                entity.setTableNumber(resultSet.getInt("table_number"));
                entity.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
                entity.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
                entity.setServingCode(resultSet.getString("serving_code"));
                entity.setPhoneNumbClient(resultSet.getString("phone_number_client"));
                entity.setClientName(resultSet.getString("client_name"));
                entity.setDeposit(resultSet.getBigDecimal("deposit"));
                entity.setManagerCode(resultSet.getString("manager_code"));
            }

        }catch (SQLException | OrderNotFoundException e){
            throw new RuntimeException(e);
        }

        return entity;
    }

    public void deleteById(Long id){
        if (id==0){
            throw new IllegalArgumentException("id має не має бути пустим");
        }
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(DELETE_ORDER_ENTITY_BY_ID);
            preparedStatement.setLong(1, id);

            preparedStatement.executeUpdate();

        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    public OrderEntity updateOrder(OrderDto dto){

        OrderEntity order = getOrderById(dto.getId());
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ORDER_ENTITY_BY_ID);

            preparedStatement.setLong(9, dto.getId());
            preparedStatement.setInt(1, dto.getTableNumber());
            preparedStatement.setTimestamp(2,Timestamp.valueOf(dto.getStartDate())); // Assuming startDate and endDate are of type java.util.Date
            preparedStatement.setTimestamp(3,Timestamp.valueOf(dto.getEndDate()));
            preparedStatement.setString(4, dto.getServingCode());
            preparedStatement.setString(5, dto.getPhoneNumbClient());
            preparedStatement.setString(6, dto.getClientName());
            preparedStatement.setBigDecimal(7, dto.getDeposit());
            preparedStatement.setString(8, dto.getManagerCode());

            int rowsAffected = preparedStatement.executeUpdate();

            // Перевіряємо, чи були внесені зміни
            if (rowsAffected == 0) {
                throw new RuntimeException("Не вдалося створити замовлення");
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return order;

    }


    public List<OrderEntity> listAll(){
        List<OrderEntity> list = new ArrayList<>();

        try{
            PreparedStatement ps = connection.prepareStatement(SELECT_ALL_ORDER);

            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()){
                OrderEntity entity = new OrderEntity();

                entity.setId(resultSet.getLong("id"));
                entity.setTableNumber(resultSet.getInt("table_number"));
                entity.setStartDate(resultSet.getTimestamp("start_date").toLocalDateTime());
                entity.setEndDate(resultSet.getTimestamp("end_date").toLocalDateTime());
                entity.setServingCode(resultSet.getString("serving_code"));
                entity.setPhoneNumbClient(resultSet.getString("phone_number_client"));
                entity.setClientName(resultSet.getString("client_name"));
                entity.setDeposit(resultSet.getBigDecimal("deposit"));
                entity.setManagerCode(resultSet.getString("manager_code"));

                list.add(entity);
            }

        }catch (SQLException e){
            throw new RuntimeException(e);
        }

        return list;
    }
}
