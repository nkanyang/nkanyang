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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ProductServiceTest {
    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductOptionRepository productOptionRepository;

    @Mock
    private ProductMapper productMapper;

    @Mock
    private ProductOptionMapper productOptionMapper;

    @Nested
    class ProductTest{
        @Test
        public void getProducts_whenNoNameSpecified_thenOK(){
            List<Product> productList = new ArrayList<>();
            productList.add(new Product());
            when(productMapper.toProductResponseDto(any(Product.class))).thenReturn(new ProductResponseDto());
            when(productRepository.findAll()).thenReturn(productList);

            ProductList result = productService.getProducts(null);

            assertEquals(1, result.getList().size());
            verify(productMapper, times(1)).toProductResponseDto(any(Product.class));
            verify(productRepository, times(1)).findAll();
        }
        @Test
        public void getProducts_whenNameSpecified_thenOK(){
            List<Product> productList = new ArrayList<>();
            productList.add(new Product());
            String name = "iphone";
            String searchString = "%" + name + "%";
            when(productMapper.toProductResponseDto(any(Product.class))).thenReturn(new ProductResponseDto());
            when(productRepository.findByNameLike(searchString)).thenReturn(productList);

            ProductList result = productService.getProducts(name);

            assertEquals(1, result.getList().size());
            verify(productRepository, times(1)).findByNameLike(searchString);
            verify(productMapper, times(1)).toProductResponseDto(any(Product.class));
        }

        @Test
        public void getProductById_thenOK(){
            when(productMapper.toProductResponseDto(any(Product.class))).thenReturn(new ProductResponseDto());
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Product()));

            productService.getProductById(UUID.randomUUID());

            verify(productRepository, times(1)).findById(any(UUID.class));
            verify(productMapper, times(1)).toProductResponseDto(any(Product.class));
        }

        @Test
        public void getProductById_whenIdNotExist_thenThrowException(){
            when(productMapper.toProductResponseDto(any(Product.class))).thenReturn(new ProductResponseDto());
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.getProductById(UUID.randomUUID()));

            verify(productRepository, times(1)).findById(any(UUID.class));
            verify(productMapper, times(0)).toProductResponseDto(any(Product.class));
        }

        @Test
        public void addProduct_thenOK(){
            when(productMapper.toProduct(any(ProductRequestDto.class))).thenReturn(new Product());
            when(productMapper.toProductResponseDto(any(Product.class))).thenReturn(new ProductResponseDto());
            when(productRepository.save(any(Product.class))).thenReturn(new Product());

            productService.addProduct(new ProductRequestDto());

            verify(productMapper, times(1)).toProduct(any(ProductRequestDto.class));
            verify(productMapper, times(1)).toProductResponseDto(any(Product.class));
            verify(productRepository, times(1)).save(any(Product.class));
        }
        @Test
        public void updateProduct_thenOK(){
            when(productMapper.updateProductFromDto(any(Product.class), any(ProductRequestDto.class))).thenReturn(new Product());
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Product()));
            when(productRepository.save(any(Product.class))).thenReturn(new Product());

            productService.updateProduct(UUID.randomUUID(), new ProductRequestDto());

            verify(productMapper, times(1)).updateProductFromDto(any(Product.class), any(ProductRequestDto.class));
            verify(productRepository, times(1)).findById(any(UUID.class));
            verify(productRepository, times(1)).save(any(Product.class));
        }
        @Test
        public void updateProduct_whenIdNotExist_thenThrowException(){
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.updateProduct(UUID.randomUUID(), new ProductRequestDto()));

            verify(productMapper, times(0)).updateProductFromDto(any(Product.class), any(ProductRequestDto.class));
            verify(productRepository, times(1)).findById(any(UUID.class));
            verify(productRepository, times(0)).save(any(Product.class));
        }

        @Test
        public void deleteProduct_thenOK(){
            when(productRepository.existsById(any(UUID.class))).thenReturn(true);
            doNothing().when(productRepository).deleteById(any(UUID.class));

            productService.deleteProduct(UUID.randomUUID());

            verify(productRepository, times(1)).existsById(any(UUID.class));
            verify(productRepository, times(1)).deleteById(any(UUID.class));
        }

        @Test
        public void deleteProduct_whenIdNotExist_thenThrowException(){
            when(productRepository.existsById(any(UUID.class))).thenReturn(false);

            assertThrows(IdNotFoundException.class, () -> productService.deleteProduct(UUID.randomUUID()));

            verify(productRepository, times(1)).existsById(any(UUID.class));
            verify(productRepository, times(0)).deleteById(any(UUID.class));
        }
    }

    @Nested
    class ProductOptionTest {
        @Test
        public void getProductOptions_whenProductIdExist_thenOK(){
            List<ProductOption> optionList = new ArrayList<>();
            optionList.add(new ProductOption());
            when(productRepository.existsById(any(UUID.class))).thenReturn(true);
            when(productOptionMapper.toProductOptionResponseDto(any(ProductOption.class))).thenReturn(new ProductOptionResponseDto());
            when(productOptionRepository.findByProductId(any(UUID.class))).thenReturn(optionList);

            ProductOptionList result = productService.getProductOptions(UUID.randomUUID());

            assertEquals(1, result.getList().size());
            verify(productRepository, times(1)).existsById(any(UUID.class));
            verify(productOptionMapper, times(1)).toProductOptionResponseDto(any(ProductOption.class));
            verify(productOptionRepository, times(1)).findByProductId(any(UUID.class));
        }
        @Test
        public void getProductOptions_whenProductIdNotExist_thenThrowException(){
            when(productRepository.existsById(any(UUID.class))).thenReturn(false);

            assertThrows(IdNotFoundException.class, () -> productService.getProductOptions(UUID.randomUUID()));

            verify(productRepository, times(1)).existsById(any(UUID.class));
        }

        @Test
        public void getProductOptionById_thenOK(){
            ProductOption option = new ProductOption();
            UUID productId = UUID.randomUUID();
            Product product = new Product();
            product.setId(productId);
            option.setProduct(product);
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));
            when(productOptionMapper.toProductOptionResponseDto(any(ProductOption.class))).thenReturn(new ProductOptionResponseDto());

            productService.getProductOptionById(productId, UUID.randomUUID());

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
            verify(productOptionMapper, times(1)).toProductOptionResponseDto(any(ProductOption.class));
        }

        @Test
        public void getProductOptionById_whenProductIdNotMatch_thenThrowException(){
            ProductOption option = new ProductOption();
            Product product = new Product();
            product.setId(UUID.randomUUID());
            option.setProduct(product);
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));

            assertThrows(IdNotMatchException.class, () -> productService.getProductOptionById(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void getProductOptionById_whenProductIdNotFound_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(new ProductOption()));

            assertThrows(IdNotFoundException.class, () -> productService.getProductOptionById(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void getProductOptionById_whenOptionIdNotExist_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.getProductOptionById(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void addProductOption_whenProductIdNotExist_thenThrowException(){
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.addProductOption(UUID.randomUUID(), new ProductOptionRequestDto()));

            verify(productRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void addProductOption_thenOK(){
            when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(new Product()));
            when(productOptionMapper.toProductOption(any(Product.class), any(ProductOptionRequestDto.class))).thenReturn(new ProductOption());
            when(productOptionRepository.save(any(ProductOption.class))).thenReturn(new ProductOption());
            when(productOptionMapper.toProductOptionResponseDto(any(ProductOption.class))).thenReturn(new ProductOptionResponseDto());

            productService.addProductOption(UUID.randomUUID(), new ProductOptionRequestDto());

            verify(productRepository, times(1)).findById(any(UUID.class));
            verify(productOptionMapper, times(1)).toProductOption(any(Product.class), any(ProductOptionRequestDto.class));
            verify(productOptionRepository, times(1)).save(any(ProductOption.class));
            verify(productOptionMapper, times(1)).toProductOptionResponseDto(any(ProductOption.class));
        }

        @Test
        public void updateProductOption_whenOptionIdNotExist_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.updateProductOption(UUID.randomUUID(), UUID.randomUUID(), new ProductOptionRequestDto()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void updateProductOption_whenProductIdNotExist_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(new ProductOption()));

            assertThrows(IdNotFoundException.class, () -> productService.updateProductOption(UUID.randomUUID(), UUID.randomUUID(), new ProductOptionRequestDto()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void updateProductOption_whenProductIdNotMatch_thenThrowException(){
            ProductOption option = new ProductOption();
            Product product = new Product();
            product.setId(UUID.randomUUID());
            option.setProduct(product);

            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));

            assertThrows(IdNotMatchException.class, () -> productService.updateProductOption(UUID.randomUUID(), UUID.randomUUID(), new ProductOptionRequestDto()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void updateProductOption_thenOK(){
            ProductOption option = new ProductOption();
            UUID productId = UUID.randomUUID();
            Product product = new Product();
            product.setId(productId);
            option.setProduct(product);
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));
            when(productOptionMapper.updateProductOptionFromDto(any(ProductOption.class), any(ProductOptionRequestDto.class))).thenReturn(new ProductOption());
            when(productOptionRepository.save(any(ProductOption.class))).thenReturn(new ProductOption());

            productService.updateProductOption(productId, UUID.randomUUID(), new ProductOptionRequestDto());

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
            verify(productOptionMapper, times(1)).updateProductOptionFromDto(any(ProductOption.class), any(ProductOptionRequestDto.class));
            verify(productOptionRepository, times(1)).save(any(ProductOption.class));
        }

        @Test
        public void deleteProductOption_whenOptionIdNotExist_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

            assertThrows(IdNotFoundException.class, () -> productService.deleteProductOption(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }

        @Test
        public void deleteProductOption_whenProductIdNotExist_thenThrowException(){
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(new ProductOption()));

            assertThrows(IdNotFoundException.class, () -> productService.deleteProductOption(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }
        @Test
        public void deleteProductOption_whenProductIdNotMatch_thenThrowException(){
            ProductOption option = new ProductOption();
            Product product = new Product();
            product.setId(UUID.randomUUID());
            option.setProduct(product);
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));

            assertThrows(IdNotMatchException.class, () -> productService.deleteProductOption(UUID.randomUUID(), UUID.randomUUID()));

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
        }
        @Test
        public void deleteProductOption_thenOK() {
            ProductOption option = new ProductOption();
            UUID productId = UUID.randomUUID();
            Product product = new Product();
            product.setId(productId);
            option.setProduct(product);
            when(productOptionRepository.findById(any(UUID.class))).thenReturn(Optional.of(option));
            doNothing().when(productOptionRepository).deleteById(any(UUID.class));

            productService.deleteProductOption(productId, UUID.randomUUID());

            verify(productOptionRepository, times(1)).findById(any(UUID.class));
            verify(productOptionRepository, times(1)).deleteById(any(UUID.class));
        }
    }
}
