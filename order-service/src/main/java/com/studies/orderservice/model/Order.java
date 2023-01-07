package com.studies.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Order {
    @Id
    private String id;
    private String orderNumber;
    private List<OrderLineItem> orderLineItemList;
}
