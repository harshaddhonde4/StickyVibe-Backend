package com.eazybytes.StickyVibe.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContactRequestDto
{
    private Long Id;

    @NotBlank(message = "Name Cannot be Empty!")
    @Size(min = 3 ,max = 30, message = "Name Must be Between 3 to 30 Characters!")
    private String name;

    @NotBlank(message = "Email Cannot be Empty!")
    @Email(message = "Please, Enter a Valid EMail Address!")
    private String email;

    @NotBlank(message = "Mobile Number Cannot be Empty!")
    @Pattern(regexp = "^\\d{10}$", message = "Mobile Number must be 10 Digits!")
    private String phone;

    @NotBlank(message = "Message Cannot be Empty!")
    @Size(min = 5, max = 300, message = "Message Must be between 5 to 300 characters!")
    private String message;
}
