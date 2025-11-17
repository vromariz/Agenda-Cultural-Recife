package br.recife.agenda.codigo.service;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.recife.agenda.codigo.dto.LoginRequest;
import br.recife.agenda.codigo.dto.RegisterRequest;
import br.recife.agenda.codigo.dto.UserResponse;
import br.recife.agenda.codigo.entity.User;
import br.recife.agenda.codigo.repository.CodigoUserRepository;

@Service
@Transactional
public class UserService {
    private final CodigoUserRepository repo;

    public UserService(CodigoUserRepository repo) {
        this.repo = repo;
    }

    public UserResponse register(RegisterRequest req) {
        if (!req.password().equals(req.confirmPassword())) {
            throw new IllegalArgumentException("Senha e confirmação não conferem"); // FAILEDLOGINATTEMPT
        }
        if (repo.existsByEmail(req.email())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado");
        }
        String hash = BCrypt.hashpw(req.password(), BCrypt.gensalt(12));
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .passwordHash(hash)
                .role(req.role() != null ? req.role() : User.UserRole.COMUM)
                .build();
        user = repo.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }

    public UserResponse login(LoginRequest request) {
    User user = repo.findByEmail(request.email())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    if (!BCrypt.checkpw(request.password(), user.getPasswordHash())) {
        throw new RuntimeException("Senha incorreta");
    }

    return new UserResponse(user.getId(), user.getName(), user.getEmail(), user.getRole());
}

}
