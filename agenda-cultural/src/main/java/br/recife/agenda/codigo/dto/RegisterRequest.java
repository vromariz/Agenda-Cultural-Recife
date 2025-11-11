package br.recife.agenda.codigo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank(message = "Nome é obrigatório")
        String name,
        @Email @NotBlank(message = "E-mail é obrigatório")
        String email,
        @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
        String password,
        @NotBlank(message = "Confirmação de senha é obrigatória")
        String confirmPassword
) {
}
