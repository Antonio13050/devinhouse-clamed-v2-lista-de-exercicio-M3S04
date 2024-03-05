package com.example.miniprojetoavaliacoess4.model.transport.operations;

import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreatePersonDTO(@NotBlank String email,
                              @NotBlank String name,
                              @NotBlank String phone,
                              @NotBlank String password,
                              @NotNull NotificationTypeEnum notificationType) {
}
