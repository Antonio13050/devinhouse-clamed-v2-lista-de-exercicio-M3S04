package com.example.miniprojetoavaliacoess4.model.transport.operations;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateBookDTO(@NotBlank String title,
                            @NotNull LocalDateTime yearPublication) {
}
