package br.recife.agenda.evento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EventoRepository extends JpaRepository<Evento, Long> {

    // Busca evento pelo nome (para evitar duplicados)
    Optional<Evento> findByNome(String nome);
}
