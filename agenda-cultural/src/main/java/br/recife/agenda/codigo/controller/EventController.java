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
}
