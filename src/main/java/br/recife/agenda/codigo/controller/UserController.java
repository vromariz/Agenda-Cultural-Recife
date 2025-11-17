package br.recife.agenda.codigo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.recife.agenda.codigo.dto.LoginRequest;
import br.recife.agenda.codigo.dto.RegisterRequest;
import br.recife.agenda.codigo.dto.UserResponse;
import br.recife.agenda.codigo.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }

     @PostMapping("/login")
    public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
        UserResponse response = service.login(request);
        return ResponseEntity.ok(response);
    }
}
