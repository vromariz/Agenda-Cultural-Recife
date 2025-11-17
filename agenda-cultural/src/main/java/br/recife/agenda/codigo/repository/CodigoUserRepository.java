package br.recife.agenda.codigo.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import br.recife.agenda.codigo.entity.User;

public interface CodigoUserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}

