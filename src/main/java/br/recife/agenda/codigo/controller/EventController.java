package br.recife.agenda.codigo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.recife.agenda.codigo.dto.EventoRequest;
import br.recife.agenda.codigo.dto.EventoResponse;
import br.recife.agenda.codigo.service.EventoService;

import java.util.List;

@RestController
@RequestMapping("/eventos")
public class EventController {

    private final EventoService service;

    public EventController(EventoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EventoResponse> register(@RequestBody EventoRequest request) {
        EventoResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

   @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<EventoResponse>> listarPorUsuario(@PathVariable Long usuarioId) {
    List<EventoResponse> eventos = service.listarEventosDoUsuario(usuarioId);
    return ResponseEntity.ok(eventos);
}

    // GET de todos (opcional)
    @GetMapping
    public ResponseEntity<List<EventoResponse>> listarTodos() {
        List<EventoResponse> eventos = service.listarTodos();
        return ResponseEntity.ok(eventos);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventoResponse> buscarPorId(@PathVariable Long id) {
        return service.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<EventoResponse> atualizar(@PathVariable Long id, @RequestBody EventoRequest request) {
        try {
            EventoResponse response = service.atualizar(id, request);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        try {
            service.excluir(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/buscar")
    public ResponseEntity<List<EventoResponse>> buscar(
            @RequestParam(required = false) String bairro,
            @RequestParam(required = false) Double precoMaximo) {
        List<EventoResponse> eventos = service.buscarPorBairroEPreco(bairro, precoMaximo);
        return ResponseEntity.ok(eventos);
    }
}
