package com.eazybytes.StickyVibe.controller;

import com.eazybytes.StickyVibe.dto.LoginRequestDto;
import com.eazybytes.StickyVibe.dto.LoginResponseDto;
import com.eazybytes.StickyVibe.dto.UserDto;
import com.eazybytes.StickyVibe.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController
{
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> apiLogin(@RequestBody LoginRequestDto loginRequestDto)
    {
        try
        {
            Authentication authentication = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.username(), loginRequestDto.password()));
            var userDto = new UserDto();
            var loggedInUser = (User) authentication.getPrincipal();
            userDto.setName(loggedInUser.getUsername());

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
}



