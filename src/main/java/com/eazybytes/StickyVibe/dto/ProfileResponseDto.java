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
    private String street;
    public String country;
    public String city;
    public String state;
    public String postalCode;
    public boolean emailUpdated;
}
