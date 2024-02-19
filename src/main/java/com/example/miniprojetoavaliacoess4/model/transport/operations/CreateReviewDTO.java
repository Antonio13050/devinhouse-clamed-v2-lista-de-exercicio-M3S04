package com.example.miniprojetoavaliacoess4.model.transport.operations;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReviewDTO(@Max(5) @Min(1) @NotNull int rating) {
}
