package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.ProductDto;
import com.eazybytes.StickyVibe.service.impl.ProductServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/products")
@RequiredArgsConstructor//Generates Constructor for final Fields
//@CrossOrigin(origins = "http://localhost:5173")
public class ProductController
{
    private final ProductServiceImpl productService;

//    @Autowired//Optional In case of single Constructor
//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() throws InterruptedException
    {
        List<ProductDto> productList = productService.getProducts();
//        System.out.println("Testing code changes2");
        return ResponseEntity.ok().body(productList);
    }
}
