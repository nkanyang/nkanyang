package com.xero.refactorthis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductResponseDto {
    @JsonProperty( value = "Id")
    private UUID id;

    @JsonProperty( value = "Name")
    private String name;

    @JsonProperty( value = "Description")
    private String description;

    @JsonProperty( value = "Price")
    private float price;

    @JsonProperty( value = "DeliveryPrice")
    private float deliveryPrice;
}
