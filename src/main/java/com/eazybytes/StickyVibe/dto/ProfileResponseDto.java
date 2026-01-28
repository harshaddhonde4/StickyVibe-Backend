package com.eazybytes.StickyVibe.dto;

import lombok.*;

@Getter
@Setter
public class ProfileResponseDto
{
    private Long customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private AddressDto address;
    public boolean emailUpdated;
}
