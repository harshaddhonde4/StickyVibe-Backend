package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.LoginRequestDto;
import com.eazybytes.StickyVibe.dto.LoginResponseDto;
import com.eazybytes.StickyVibe.dto.RegisterRequestDto;
import com.eazybytes.StickyVibe.dto.UserDto;
import com.eazybytes.StickyVibe.entity.Customer;
import com.eazybytes.StickyVibe.repository.CustomerRepository;
import com.eazybytes.StickyVibe.util.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.authentication.password.CompromisedPasswordDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final CustomerRepository customerRepository;
    private final CompromisedPasswordChecker compromisedPasswordChecker;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto)
    {
        try
        {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
            var userDto = new UserDto();
            var loggedInUser = (Customer) authentication.getPrincipal();
            userDto.setName(loggedInUser.getName());
            userDto.setEmail(loggedInUser.getEmail());
            userDto.setPhone(loggedInUser.getMobileNumber());
            String jwt = jwtUtil.generateJwtToken(authentication);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new LoginResponseDto(HttpStatus.OK.getReasonPhrase(), userDto, jwt));
        }
        catch (BadCredentialsException ex)
        {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
        catch (AuthenticationException ex)
        {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Authentication failed");
        }
        catch (Exception ex)
        {
            return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred");
        }
    }

    private ResponseEntity<LoginResponseDto> buildErrorResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status).body(new LoginResponseDto(message, null, null));
    }

    @PostMapping("/register")
    public ResponseEntity<?> createUser(@RequestBody @Valid RegisterRequestDto registerRequestDto) {
        CompromisedPasswordDecision decision = compromisedPasswordChecker.check(registerRequestDto.getPassword());
        if (decision.isCompromised()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("password", "Weak Password, Please Enter a Strong Password"));
        }
        Optional<Customer> existingCustomer = customerRepository.findByEmailOrMobileNumber(registerRequestDto.getEmail(), registerRequestDto.getPhone());
        if (existingCustomer.isPresent()) {
            Map<String, String> errors = new HashMap<>();
            Customer customer = existingCustomer.get();
            if (customer.getEmail().equalsIgnoreCase(registerRequestDto.getEmail())) {
                errors.put("email", "Email already exists");
            }
            if (customer.getMobileNumber().equalsIgnoreCase(registerRequestDto.getPhone())) {
                errors.put("phone", "Phone number already exists");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(errors);
        }
        Customer customer = new Customer();
        BeanUtils.copyProperties(registerRequestDto, customer);
        customer.setMobileNumber(registerRequestDto.getPhone());
        customer.setPasswordHash(passwordEncoder.encode(registerRequestDto.getPassword()));
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Registration successful");
    }
}







