package com.xero.refactorthis.mapper;

import com.xero.refactorthis.dto.ProductOptionRequestDto;
import com.xero.refactorthis.dto.ProductOptionResponseDto;
import com.xero.refactorthis.entity.Product;
import com.xero.refactorthis.entity.ProductOption;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProductOptionMapper {
    public ProductOptionResponseDto toProductOptionResponseDto(ProductOption option);

    @Mapping(target = "product", source = "product")
    @Mapping(target = "name", source = "optionDto.name")
    @Mapping(target = "description", source = "optionDto.description")
    @Mapping(target = "id", ignore = true)
    public ProductOption toProductOption(Product product, ProductOptionRequestDto optionDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    public ProductOption updateProductOptionFromDto(@MappingTarget ProductOption option, ProductOptionRequestDto optionDto);
}
