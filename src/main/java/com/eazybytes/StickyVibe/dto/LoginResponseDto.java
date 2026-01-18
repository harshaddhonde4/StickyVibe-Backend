package com.eazybytes.StickyVibe.dto;

public record LoginResponseDto(String message, UserDto user, String jwtToken)
{
}
