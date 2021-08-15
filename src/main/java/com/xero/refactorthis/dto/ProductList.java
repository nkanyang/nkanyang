package com.xero.refactorthis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductList {
    @JsonProperty( value = "Items")
    private List<ProductResponseDto> list = new ArrayList<>();
}
