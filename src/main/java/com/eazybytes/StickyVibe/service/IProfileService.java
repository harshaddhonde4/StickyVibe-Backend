package com.eazybytes.StickyVibe.service;

import com.eazybytes.StickyVibe.dto.ProfileRequestDto;
import com.eazybytes.StickyVibe.dto.ProfileResponseDto;

public interface IProfileService
{
    ProfileResponseDto getProfile();
    ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto);
}

