package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.ProfileRequestDto;
import com.eazybytes.StickyVibe.dto.ProfileResponseDto;
import com.eazybytes.StickyVibe.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final IProfileService profileService;

    @GetMapping
    public ResponseEntity<ProfileResponseDto> getProfile()
    {
        ProfileResponseDto profileResponseDto = profileService.getProfile();
        return ResponseEntity.ok(profileResponseDto);
    }

    @PutMapping
    public ResponseEntity<ProfileResponseDto> updateProfile(@RequestBody @Validated ProfileRequestDto profileRequestDto) {
        ProfileResponseDto profileResponseDto = profileService.updateProfile(profileRequestDto);
        return ResponseEntity.ok(profileResponseDto);
    }

}
