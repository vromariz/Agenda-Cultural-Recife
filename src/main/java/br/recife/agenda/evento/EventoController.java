package br.recife.agenda.evento;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    // Listar todos os eventos
    @GetMapping
    public ResponseEntity<List<Evento>> listarEventos() {
        List<Evento> eventos = service.listarTodos();
        return ResponseEntity.ok(eventos);
    }

    // Buscar evento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Evento> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar novo evento
    @PostMapping
    public ResponseEntity<?> criarEvento(@RequestBody Evento evento) {
        try {
            Evento salvo = service.salvar(evento);
            return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
        } catch (IllegalArgumentException e) {
            // Erros de validação do service (nome duplicado, campo vazio etc.)
            return ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErroResponse(HttpStatus.CONFLICT.value(), "Erro ao criar evento", e.getMessage())
            );
        } catch (Exception e) {
            // Qualquer outro erro inesperado
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErroResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Erro interno", e.getMessage())
            );
        }
    }
}

