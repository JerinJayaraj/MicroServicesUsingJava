package com.studies.orderservice.service;

import com.studies.orderservice.dto.OrderLineItemDto;
import com.studies.orderservice.dto.OrderRequest;
import com.studies.orderservice.model.Order;
import com.studies.orderservice.model.OrderLineItem;
import com.studies.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItemList(orderLineItemList);
        orderRepository.save(order);
    }

    private OrderLineItem mapToDto(OrderLineItemDto orderLineItemDto) {
        OrderLineItem orderLineItem = OrderLineItem.builder()
                .skuCode(orderLineItemDto.getSkuCode())
                .price(orderLineItemDto.getPrice())
                .quantity(orderLineItemDto.getQuantity())
                .build();
        return orderLineItem;
    }

    public List<Order> getAllOrders(){
        return orderRepository.findAll();
    }
}
