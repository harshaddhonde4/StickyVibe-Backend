package com.eazybytes.StickyVibe.service.impl;

import com.eazybytes.StickyVibe.dto.ProfileRequestDto;
import com.eazybytes.StickyVibe.dto.ProfileResponseDto;
import com.eazybytes.StickyVibe.entity.Address;
import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.repository.CustomerRepository;
import com.eazybytes.StickyVibe.service.IProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements IProfileService
{   private final CustomerRepository customerRepository;
    @Override
    public ProfileResponseDto getProfile()
    {
        Customer customer = getAuthenticatedCustomer();
        return mapCustomerToProfileResponseDto(customer);
    }

    private Customer getAuthenticatedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return customerRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

        private ProfileResponseDto mapCustomerToProfileResponseDto(Customer customer) {
            ProfileResponseDto profileResponseDto = new ProfileResponseDto();
            BeanUtils.copyProperties(customer, profileResponseDto);
            if (customer.getAddress() != null) {
                profileResponseDto.setStreet(customer.getAddress().getStreet());
                profileResponseDto.setCity(customer.getAddress().getCity());
                profileResponseDto.setState(customer.getAddress().getState());
                profileResponseDto.setPostalCode(customer.getAddress().getPostalCode());
                profileResponseDto.setCountry(customer.getAddress().getCountry());
            }
            return profileResponseDto;
        }

    @Override
    public ProfileResponseDto updateProfile(ProfileRequestDto profileRequestDto) {
        Customer customer = getAuthenticatedCustomer();
        boolean isEmailUpdated = !customer.getEmail().equals(profileRequestDto.getEmail().trim());
        BeanUtils.copyProperties(profileRequestDto, customer);
        Address address = customer.getAddress();
        if (address == null) {
            address = new Address();
            address.setCustomer(customer);
        }
        address.setStreet(profileRequestDto.getStreet());
        address.setCity(profileRequestDto.getCity());
        address.setState(profileRequestDto.getState());
        address.setPostalCode(profileRequestDto.getPostalCode());
        address.setCountry(profileRequestDto.getCountry());
        customer.setAddress(address);
        customer = customerRepository.save(customer);
        ProfileResponseDto profileResponseDto = mapCustomerToProfileResponseDto(customer);
        profileResponseDto.setEmailUpdated(isEmailUpdated);
        return profileResponseDto;
    }
}
