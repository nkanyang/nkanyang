package com.xero.refactorthis.mapper;

import com.xero.refactorthis.dto.ProductRequestDto;
import com.xero.refactorthis.dto.ProductResponseDto;
import com.xero.refactorthis.entity.Product;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    public Product toProduct(ProductRequestDto dto);

    public ProductResponseDto toProductResponseDto(Product product);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public Product updateProductFromDto(@MappingTarget Product product, ProductRequestDto optionDto);

}
