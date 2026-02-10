package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.ContactInfoDto;
import com.eazybytes.StickyVibe.dto.ContactRequestDto;
import com.eazybytes.StickyVibe.dto.ProductDto;
import com.eazybytes.StickyVibe.service.impl.ContactServiceImpl;
import com.eazybytes.StickyVibe.service.impl.ProductServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/contacts")
@RequiredArgsConstructor//Generates Constructor for final Fields
public class ContactController
{
    private final ContactServiceImpl contactServiceImpl;
    private final ContactInfoDto contactInfoDto;

//    @Autowired//Optional In case of single Constructor
//    public ProductController(ProductRepository productRepository) {
//        this.productRepository = productRepository;
//    }

    @PostMapping
    public ResponseEntity<String> saveContacts(
            @Valid @RequestBody ContactRequestDto contactRequestDto)
    {
        contactServiceImpl.saveContact(contactRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Request Proceed SuccessFully!");
    }

    @GetMapping()
    public ResponseEntity<ContactInfoDto> getContactInfo()
    {
        return ResponseEntity.ok(contactInfoDto);
    }
}
