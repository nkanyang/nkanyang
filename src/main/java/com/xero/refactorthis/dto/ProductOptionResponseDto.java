package com.xero.refactorthis.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.UUID;

@Data
public class ProductOptionResponseDto {
    @JsonProperty(value = "Id")
    private UUID id;

    @JsonProperty(value = "Name")
    private String name;

    @JsonProperty(value = "Description")
    private String description;
}
