package com.example.B3_Social.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserRecordDTO(
        @NotBlank String username,
        @NotBlank String name,
        @NotBlank @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres") String password,
        @NotBlank String classification) {

}
