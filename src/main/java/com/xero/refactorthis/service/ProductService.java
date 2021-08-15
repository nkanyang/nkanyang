package com.xero.refactorthis.service;

import com.xero.refactorthis.dto.*;
import com.xero.refactorthis.entity.Product;
import com.xero.refactorthis.entity.ProductOption;
import com.xero.refactorthis.exception.IdNotFoundException;
import com.xero.refactorthis.exception.IdNotMatchException;
import com.xero.refactorthis.mapper.ProductMapper;
import com.xero.refactorthis.mapper.ProductOptionMapper;
import com.xero.refactorthis.repository.ProductOptionRepository;
import com.xero.refactorthis.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;


@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductOptionRepository productOptionRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ProductOptionMapper productOptionMapper;

    Logger logger = LoggerFactory.getLogger(ProductService.class);

    public ProductList getProducts(String productName) {
        ProductList list = new ProductList();
        if(productName == null) {
            productRepository.findAll()
                    .forEach(product -> list.getList().add(productMapper.toProductResponseDto(product)));
        }
        else {
            productRepository.findByNameLike("%" + productName + "%")
                    .forEach(product -> list.getList().add(productMapper.toProductResponseDto(product)));
        }
        return list;
    }

    public ProductResponseDto getProductById(UUID id) {
        Product product = productRepository.findById(id).orElseThrow(IdNotFoundException::new);
        return productMapper.toProductResponseDto(product);
    }

    public ProductResponseDto addProduct(ProductRequestDto productCreateDto) {
        Product product = productMapper.toProduct(productCreateDto);
        productRepository.save(product);
        return productMapper.toProductResponseDto(product);
    }

    public void updateProduct(UUID id, ProductRequestDto productRequestDto) {
        Product product = productRepository.findById(id).orElseThrow(IdNotFoundException::new);
        productRepository.save(productMapper.updateProductFromDto(product, productRequestDto));
    }

    public void deleteProduct(UUID id) {
        if(!productRepository.existsById(id)) {
            logger.debug("ID: " + id + " of product not found.");
            throw new IdNotFoundException();
        }
        productRepository.deleteById(id);
    }

    public ProductOptionList getProductOptions(UUID id) {
        if(!productRepository.existsById(id)) {
            logger.debug("ID: " + id + " of product not found.");
            throw new IdNotFoundException();
        }
        ProductOptionList list = new ProductOptionList();
        productOptionRepository.findByProductId(id)
                .forEach(option -> list.getList().add(productOptionMapper.toProductOptionResponseDto(option)));
        return list;
    }

    public ProductOptionResponseDto getProductOptionById(UUID productId, UUID optionId) {
        ProductOption option = productOptionRepository.findById(optionId).orElseThrow(IdNotFoundException::new);
        validateProduct(option.getProduct(), productId);
        return productOptionMapper.toProductOptionResponseDto(option);
    }

    public ProductOptionResponseDto addProductOption(UUID id, ProductOptionRequestDto optionDto) {
        Product product = productRepository.findById(id).orElseThrow(IdNotFoundException::new);
        ProductOption option = productOptionMapper.toProductOption(product, optionDto);
        productOptionRepository.save(option);
        return productOptionMapper.toProductOptionResponseDto(option);
    }

    public void updateProductOption(UUID productId, UUID optionId, ProductOptionRequestDto optionDto) {
        ProductOption option = productOptionRepository.findById(optionId).orElseThrow(IdNotFoundException::new);
        validateProduct(option.getProduct(), productId);
        productOptionRepository.save(productOptionMapper.updateProductOptionFromDto(option, optionDto));
    }

    public void deleteProductOption(UUID productId, UUID optionId) {
        ProductOption option = productOptionRepository.findById(optionId).orElseThrow(() -> new IdNotFoundException());
        validateProduct(option.getProduct(), productId);
        productOptionRepository.deleteById(optionId);
    }

    private void validateProduct(Product product, UUID givenId){
        try{
            if(product == null){
                throw new IdNotFoundException(givenId, "product");
            }
            if(!product.getId().equals(givenId)){
                throw new IdNotMatchException(givenId);
            }
        }catch (RuntimeException e){
            logger.debug(e.getMessage());
            throw e;
        }
    }
}
