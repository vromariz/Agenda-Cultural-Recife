package br.recife.agenda.codigo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public record EventoRequest(
        @NotBlank(message = "Nome do evento é obrigatório")
        String nome,

        @NotBlank(message = "Descrição é obrigatória")
        String descricao,

        @NotBlank(message = "Local é obrigatório")
        String local,

        @NotNull(message = "Data é obrigatória")
        LocalDate data,

        @NotBlank(message = "Categoria é obrigatória")
        String categoria,
        
        String bairro,
        
        Double preco,

        @NotNull(message = "ID do usuário é obrigatório")
        Long usuarioId
) {}
