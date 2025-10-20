package br.recife.agenda.user;

import br.recife.agenda.user.dto.RegisterRequest;
import br.recife.agenda.user.dto.UserResponse;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public UserResponse register(RegisterRequest req) {
        if (!req.password().equals(req.confirmPassword())) {
            throw new IllegalArgumentException("Senha e confirmação não conferem");
        }
        if (repo.existsByEmail(req.email())) {
            throw new DataIntegrityViolationException("E-mail já cadastrado");
        }
        String hash = BCrypt.hashpw(req.password(), BCrypt.gensalt(12));
        User user = User.builder()
                .name(req.name())
                .email(req.email())
                .passwordHash(hash)
                .build();
        user = repo.save(user);
        return new UserResponse(user.getId(), user.getName(), user.getEmail());
    }

    public User findByEmail(String email) {
        return repo.findByEmail(email).orElse(null);
    }
}
