package br.recife.agenda.codigo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.recife.agenda.codigo.dto.EventoRequest;
import br.recife.agenda.codigo.dto.EventoResponse;
import br.recife.agenda.codigo.entity.Eventos;
import br.recife.agenda.codigo.entity.User;
import br.recife.agenda.codigo.repository.EventoRepository;
import br.recife.agenda.codigo.repository.UserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventoService {

        private final EventoRepository eventoRepo;
        private final UserRepository userRepo;

        public EventoService(EventoRepository eventoRepo, UserRepository userRepo) {
                this.eventoRepo = eventoRepo;
                this.userRepo = userRepo;
        }

        // Cria um novo evento para um usuário
        @Transactional
        public EventoResponse register(EventoRequest req) {
                User user = userRepo.findById(req.usuarioId())
                                .orElseThrow(() -> new RuntimeException("Usuário não encontrado")); // USERNOTFOUNTEXCEPTION

                Eventos evento = new Eventos();
                evento.setNome(req.nome());
                evento.setDescricao(req.descricao());
                evento.setLocal(req.local());
                evento.setData(req.data());
                evento.setCategoria(req.categoria());
                evento.setUsuario(user);

                evento = eventoRepo.save(evento);

                return new EventoResponse(
                                evento.getId(),
                                evento.getNome(),
                                evento.getDescricao(),
                                evento.getLocal(),
                                evento.getData(),
                                evento.getCategoria());
        }

        // Lista todos os eventos de um usuário
    public List<EventoResponse> listarEventosDoUsuario(Long usuarioId) {
    // Verifica se o usuário existe
        userRepo.findById(usuarioId)
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

    // Busca eventos pelo ID do usuário
         return eventoRepo.findByUsuarioId(usuarioId).stream()
                .map(e -> new EventoResponse(
                    e.getId(),
                    e.getNome(),
                    e.getDescricao(),
                    e.getLocal(),
                    e.getData(),
                    e.getCategoria()
                ))
                .toList();
        }

        public List<EventoResponse> listarTodos() {
                return eventoRepo.findAll().stream()
                                .map(e -> new EventoResponse(
                                                e.getId(),
                                                e.getNome(),
                                                e.getDescricao(),
                                                e.getLocal(),
                                                e.getData(),
                                                e.getCategoria()))
                                .toList();
        }
}
