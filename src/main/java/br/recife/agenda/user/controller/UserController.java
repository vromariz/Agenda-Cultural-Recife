package br.recife.agenda.user.controller;

import br.recife.agenda.user.UserService;
import br.recife.agenda.user.dto.RegisterRequest;
import br.recife.agenda.user.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest request) {
        UserResponse response = service.register(request);
        return ResponseEntity.ok(response);
    }
}
