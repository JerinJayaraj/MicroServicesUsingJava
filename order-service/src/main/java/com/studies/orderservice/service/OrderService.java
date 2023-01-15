package com.studies.orderservice.service;

import com.studies.orderservice.dto.InventoryResponse;
import com.studies.orderservice.dto.OrderLineItemDto;
import com.studies.orderservice.dto.OrderRequest;
import com.studies.orderservice.model.Order;
import com.studies.orderservice.model.OrderLineItem;
import com.studies.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private OrderRepository orderRepository;

    private WebClient webClient;
    @Autowired
    public OrderService(WebClient.Builder webClientBuilder,  OrderRepository orderRepository) {
//        this.webClient = WebClient.builder()
//                .baseUrl("http://inventory-service").build();
        this.webClient = webClientBuilder.baseUrl("http://inventory-service").build();
        this.orderRepository = orderRepository;
    }

    public String placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLineItem> orderLineItemList = orderRequest.getOrderLineItemDtoList()
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
        order.setOrderLineItemList(orderLineItemList);
        List<String> skuCodes = orderRequest.getOrderLineItemDtoList().stream()
                .map(OrderLineItemDto::getSkuCode)
                .collect(Collectors.toList());
        /* Call inventory service and place order if the product is in stock */
        /* http://localhost:8082/api/inventory?skuCode=iphone_13&skuCode=iphone_13_red */
        InventoryResponse[] inventoryResponseArray = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventory")
                        .queryParam("skuCode", skuCodes.toArray())
                        .build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        if(inventoryResponseArray.length > 0){
//            boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(inventoryResponse -> inventoryResponse.isInStock());
            boolean allProductsInStock = skuCodes.size() == inventoryResponseArray.length;
            if (allProductsInStock) {
                orderRepository.save(order);
                return "Order placed successfully";
            }
            else {
                return "Product is not in stock.. Please try again later";
            }
        }
        else {
            return "Product is not in stock.. Please try again later";
        }
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
