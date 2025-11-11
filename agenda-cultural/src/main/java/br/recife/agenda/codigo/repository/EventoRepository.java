package br.recife.agenda.codigo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.recife.agenda.codigo.entity.Eventos;

import java.util.List;

@Repository
public interface EventoRepository extends JpaRepository<Eventos, Long> {
    List<Eventos> findByUsuarioId(Long usuarioId);
}