package com.example.miniprojetoavaliacoess4.model.transport.operations;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonDTO(@NotBlank String email,
                              @NotBlank String name,
                              @NotBlank String password) {
}
