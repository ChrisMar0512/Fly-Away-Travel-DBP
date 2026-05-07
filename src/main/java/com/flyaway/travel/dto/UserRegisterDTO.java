package com.flyaway.travel.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class UserRegisterDTO {
    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "Debe contener al menos 1 letra mayúscula")
    private String firstName;

    @NotBlank
    @Pattern(regexp = ".*[A-Z].*", message = "Debe contener al menos 1 letra mayúscula")
    private String lastName;

    @NotBlank
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d).{8,}$", message = "Mínimo 8 caracteres, al menos 1 letra y 1 número")
    private String password;
}
