package br.recife.agenda.codigo.dto;

import java.time.LocalDate;

public record EventoResponse(
        Long id,
        String nome,
        String descricao,
        String local,
        LocalDate data,
        String categoria,
        String bairro,
        Double preco
) {}
