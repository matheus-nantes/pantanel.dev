package com.example.B3_Social.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ONGRecordDTO(@NotBlank String name, @NotBlank String description) {

}
