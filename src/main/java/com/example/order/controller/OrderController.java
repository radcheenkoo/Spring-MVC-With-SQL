package com.example.order.controller;

import com.example.exception.OrderAlreadyExistException;
import com.example.exception.OrderNotFoundException;
import com.example.mapper.OrderMapper;
import com.example.order.data.OrderDto;
import com.example.order.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Objects;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService service;

    @Autowired
    private OrderMapper mapper;

    @GetMapping("/list")
    public ModelAndView list() {
        ModelAndView result = new ModelAndView("order_list");
        result.addObject("orders", mapper.toDtos(service.listAll()));
        return result;
    }

    @PostMapping("/create")
    public String createOrder(@ModelAttribute("orderDto") OrderDto orderDto) {

        if (Objects.isNull(orderDto)){
            throw new IllegalArgumentException("Required parameters cannot be null");
        }

        try {
            service.createOrder(orderDto);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/orders/list";
    }

    @PostMapping("/edit")
    public String updateOrder(@ModelAttribute("orderDto") OrderDto orderDto) {
        service.updateOrder(orderDto);
        return "redirect:/orders/list";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable(value = "id") Long id) throws OrderNotFoundException {
        service.deleteById(id);
        return "redirect:/orders/list";
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteView(@PathVariable(value = "id") Long id) throws OrderNotFoundException {
        OrderDto orderDto = mapper.toOrderDto(service.getOrderById(id));
        ModelAndView modelAndView = new ModelAndView("delete_order_by_id");
        modelAndView.addObject("orderDto", orderDto);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView showCreateForm() {
        ModelAndView modelAndView = new ModelAndView("create_order");
        modelAndView.addObject("orderDto", new OrderDto());
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) throws OrderNotFoundException {
        OrderDto orderDto = mapper.toOrderDto(service.getOrderById(id));
        ModelAndView modelAndView = new ModelAndView("edit_by_id");
        modelAndView.addObject("orderDto", orderDto);
        return modelAndView;
    }
}

