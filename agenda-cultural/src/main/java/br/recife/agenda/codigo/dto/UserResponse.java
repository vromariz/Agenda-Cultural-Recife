package br.recife.agenda.codigo.dto;

import br.recife.agenda.codigo.entity.User.UserRole;

public record UserResponse(Long id, String name, String email, UserRole role) {}
