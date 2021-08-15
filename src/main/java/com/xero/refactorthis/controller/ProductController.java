package com.xero.refactorthis.controller;

import com.xero.refactorthis.dto.*;
import com.xero.refactorthis.service.ProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Gets all products, returns an object contains a list of products.",
            notes = "If parameter 'name' is specified, finds all products matching the name.")
    public ProductList getProducts(@RequestParam(required = false) String name) {
        return productService.getProducts(name);
    }

    @GetMapping("{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Gets the project that matches the specified ID",
            notes = "ID is a GUID")
    public ProductResponseDto getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Creates a new product.",
            notes = "Request Body Properties: \n " +
                    "Product Name:  A string no more than 128 characters. Required.\n " +
                    "Product Description:  A string no more than 1024 characters. Required.\n " +
                    "Price:  A decimal number larger than 0. Required.\n " +
                    "Delivery Price:  A decimal number larger than 0. Required.")
    public ProductResponseDto addProduct(@Validated @RequestBody ProductRequestDto product) {
        return productService.addProduct(product);
    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Updates a product.",
            notes = "Request Body Properties: \n " +
                    "Product Name:  A string no more than 128 characters. Required.\n " +
                    "Product Description:  A string no more than 1024 characters. Required.\n " +
                    "Price:  A decimal number larger than 0. Required.\n " +
                    "Delivery Price:  A decimal number larger than 0. Required.")
    public void updateProduct(@PathVariable UUID id, @Validated @RequestBody ProductRequestDto product) {
        productService.updateProduct(id, product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes a product and its options.")
    public void deleteProduct(@PathVariable UUID id) {
        productService.deleteProduct(id);
    }

    @GetMapping("{id}/options")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Finds all options for a product specified by ID, returns an object contains a list of product options.")
    public ProductOptionList getProductOptions(@PathVariable UUID id) {
        return productService.getProductOptions(id);
    }

    @GetMapping("{id}/options/{optionId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Finds the specified option for the specified product.")
    public ProductOptionResponseDto getProductOptionById(@PathVariable UUID id, @PathVariable UUID optionId) {
        return productService.getProductOptionById(id, optionId);
    }

    @PostMapping("{id}/options")
    @ResponseStatus(value = HttpStatus.CREATED)
    @ApiOperation(value = "Adds a new product option to the specified product.",
            notes = "Request Body Properties: \n " +
                    "Option Name:  A string no more than 128 characters. Required.\n " +
                    "Option Description:  A string no more than 1024 characters. Required.")
    public ProductOptionResponseDto addProductOption(@PathVariable UUID id,
                                                     @Validated @RequestBody ProductOptionRequestDto option) {
        return productService.addProductOption(id, option);
    }

    @PutMapping("{id}/options/{optionId}")
    @ResponseStatus(value = HttpStatus.OK)
    @ApiOperation(value = "Updates the specified product option.",
            notes = "Request Body Properties: \n " +
                    "Option Name:  A string no more than 128 characters. Required.\n " +
                    "Option Description:  A string no more than 1024 characters. Required.")
    public void updateProductOption(@PathVariable UUID id,
                                    @PathVariable UUID optionId,
                                    @Validated @RequestBody ProductOptionRequestDto option) {
        productService.updateProductOption(id, optionId, option);
    }

    @DeleteMapping("{id}/options/{optionId}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Deletes the specified option of the specified product.")
    public void deleteProductOption(@PathVariable UUID id,
                                    @PathVariable UUID optionId) {
        productService.deleteProductOption(id, optionId);
    }
}
