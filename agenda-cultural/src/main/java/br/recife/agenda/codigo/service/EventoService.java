package br.recife.agenda.codigo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.recife.agenda.codigo.dto.EventoRequest;
import br.recife.agenda.codigo.dto.EventoResponse;
import br.recife.agenda.codigo.entity.Eventos;
import br.recife.agenda.codigo.entity.User;
import br.recife.agenda.codigo.repository.EventosRepository;
import br.recife.agenda.codigo.repository.CodigoUserRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class EventoService {

        private final EventosRepository eventoRepo;
        private final CodigoUserRepository userRepo;

        public EventoService(EventosRepository eventoRepo, CodigoUserRepository userRepo) {
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
                evento.setBairro(req.bairro());
                evento.setPreco(req.preco());
                evento.setUsuario(user);

                evento = eventoRepo.save(evento);

                return new EventoResponse(
                                evento.getId(),
                                evento.getNome(),
                                evento.getDescricao(),
                                evento.getLocal(),
                                evento.getData(),
                                evento.getCategoria(),
                                evento.getBairro(),
                                evento.getPreco());
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
                    e.getCategoria(),
                    e.getBairro(),
                    e.getPreco()
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
                                                e.getCategoria(),
                                                e.getBairro(),
                                                e.getPreco()))
                                .toList();
        }
        
        public java.util.Optional<EventoResponse> buscarPorId(Long id) {
                return eventoRepo.findById(id)
                                .map(e -> new EventoResponse(
                                                e.getId(),
                                                e.getNome(),
                                                e.getDescricao(),
                                                e.getLocal(),
                                                e.getData(),
                                                e.getCategoria(),
                                                e.getBairro(),
                                                e.getPreco()));
        }
        
        @Transactional
        public EventoResponse atualizar(Long id, EventoRequest req) {
                Eventos evento = eventoRepo.findById(id)
                                .orElseThrow(() -> new RuntimeException("Evento não encontrado"));
                
                evento.setNome(req.nome());
                evento.setDescricao(req.descricao());
                evento.setLocal(req.local());
                evento.setData(req.data());
                evento.setCategoria(req.categoria());
                evento.setBairro(req.bairro());
                evento.setPreco(req.preco());
                
                evento = eventoRepo.save(evento);
                
                return new EventoResponse(
                                evento.getId(),
                                evento.getNome(),
                                evento.getDescricao(),
                                evento.getLocal(),
                                evento.getData(),
                                evento.getCategoria(),
                                evento.getBairro(),
                                evento.getPreco());
        }
        
        @Transactional
        public void excluir(Long id) {
                if (!eventoRepo.existsById(id)) {
                        throw new RuntimeException("Evento não encontrado");
                }
                eventoRepo.deleteById(id);
        }
        
        public List<EventoResponse> buscarPorBairroEPreco(String bairro, Double precoMaximo) {
                return eventoRepo.findAll().stream()
                                .filter(e -> (bairro == null || bairro.isBlank() || 
                                             (e.getBairro() != null && e.getBairro().equalsIgnoreCase(bairro)))
                                && (precoMaximo == null || e.getPreco() == null || e.getPreco() <= precoMaximo))
                                .map(e -> new EventoResponse(
                                                e.getId(),
                                                e.getNome(),
                                                e.getDescricao(),
                                                e.getLocal(),
                                                e.getData(),
                                                e.getCategoria(),
                                                e.getBairro(),
                                                e.getPreco()))
                                .toList();
        }
}
