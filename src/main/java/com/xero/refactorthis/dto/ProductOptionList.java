package com.xero.refactorthis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ProductOptionList {
    @JsonProperty(value = "Items")
    private List<ProductOptionResponseDto> list = new ArrayList<>();
}
