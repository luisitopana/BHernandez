package com.lmh.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.lmh.dto.LoginRequest;
import com.lmh.dto.LoginResponse;
import com.lmh.security.JwtService;

@RestController
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    
    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    // ... constructor ...

    @PostMapping("/api/login")
    public ResponseEntity<LoginResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        // 1. Spring Security valida las credenciales.
        // Si son incorrectas, lanza una excepción que es manejada por Spring.
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.username(), loginRequest.password())
        );

        // 2. Si la autenticación es exitosa, generamos el token.
        // El objeto 'authentication' contiene toda la info del usuario validado.
        String jwt = jwtService.generateToken(authentication);

        // 3. Devolvemos el token.
        return ResponseEntity.ok(new LoginResponse(jwt));
    }
}
