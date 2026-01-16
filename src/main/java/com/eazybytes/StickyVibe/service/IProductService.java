package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.ProductDto;

import java.util.List;

//Built service Layer with interface and implementation classes- Good Approach
public interface IProductService
{
    List<ProductDto> getProducts();
}
