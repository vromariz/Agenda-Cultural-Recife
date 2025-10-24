package br.recife.agenda.evento;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    private final EventoRepository repository;

    public EventoService(EventoRepository repository) {
        this.repository = repository;
    }

    public List<Evento> listarTodos() {
        return repository.findAll();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return repository.findById(id);
    }

    public Evento salvar(Evento evento) {

        // 1. Verificar se campos obrigatórios estão preenchidos
        if (evento.getNome() == null || evento.getNome().isBlank()) {
            throw new IllegalArgumentException("O nome do evento é obrigatório.");
        }
        if (evento.getLocal() == null || evento.getLocal().isBlank()) {
            throw new IllegalArgumentException("O local do evento é obrigatório.");
        }
        if (evento.getDataEvento() == null || evento.getDataEvento().isBlank()) {
            throw new IllegalArgumentException("A data do evento é obrigatória.");
        }
        if (evento.getCategoria() == null || evento.getCategoria().isBlank()) {
            throw new IllegalArgumentException("A categoria do evento é obrigatória.");
        }

        // 2. Verificar se já existe evento com o mesmo nome
        repository.findByNome(evento.getNome()).ifPresent(e -> {
            throw new IllegalArgumentException("Já existe um evento com este nome.");
        });

        // 3. Salvar evento se estiver tudo certo
        return repository.save(evento);
    }
}
