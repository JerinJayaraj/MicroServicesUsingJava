package com.studies.orderservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Document
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class OrderLineItem {
    @Id
    private String id;
    private String skuCode;
    private BigDecimal price;
    private Integer quantity;
}
