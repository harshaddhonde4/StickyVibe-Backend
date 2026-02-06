package com.eazybytes.StickyVibe.service.impl;

import com.eazybytes.StickyVibe.dto.ProductDto;
import com.eazybytes.StickyVibe.entity.Product;
import com.eazybytes.StickyVibe.repository.ProductRepository;
import com.eazybytes.StickyVibe.service.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service//Telling Spring that this is a Service Layer
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService
{
    private final ProductRepository productRepository;

    @Cacheable("products")
    @Override
    public List<ProductDto> getProducts() {
        return productRepository.findAll().
                stream().map(this::transformToDto).collect(Collectors.toList());
    }

    //Mapper Logic for DTO
    private ProductDto transformToDto(Product product)
    {
        ProductDto productDto = new ProductDto();
        //In Case when fields name in entity class and DTO is same
        BeanUtils.copyProperties(product, productDto);
        productDto.setProductId(product.getId());
        return productDto;
    }
}
