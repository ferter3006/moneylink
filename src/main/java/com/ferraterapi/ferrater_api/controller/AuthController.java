package com.ferraterapi.ferrater_api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ferraterapi.ferrater_api.dto.ApiResponse;
import com.ferraterapi.ferrater_api.dto.LoginRequest;
import com.ferraterapi.ferrater_api.dto.RegistroRequest;
import com.ferraterapi.ferrater_api.model.Usuario;
import com.ferraterapi.ferrater_api.repository.ApiKeyRepository;
import com.ferraterapi.ferrater_api.repository.UsuarioRepository;
import com.ferraterapi.ferrater_api.service.AuthService;
import com.ferraterapi.ferrater_api.service.UsuarioService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    public final ApiKeyRepository apiKeyRepository;
    public final UsuarioRepository usuarioRepository;
    public final UsuarioService usuarioService;
    public final AuthService authService;

    // Constructor
    public AuthController(ApiKeyRepository apiKeyRepository, UsuarioRepository usuarioRepository,
            UsuarioService usuarioService, AuthService authService) {
        this.apiKeyRepository = apiKeyRepository;
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
        this.authService = authService;

    }

    // --------------------
    // Login de usuarios
    // --------------------
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<Map<String, String>>> login(@RequestBody LoginRequest request) {
        Map<String, String> data = new HashMap<>();
        String message = "";

        String email = request.getEmail();
        String password = request.getPassword();

        // Si campos vacios retornar status 0 con error
        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            return ResponseEntity
                    .ok(new ApiResponse<>(0, "AuthController.java", "email y password no pueden ser vacíos", null));
        }

        // Busco el usuario en la base
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);

        // Comprobamos Si el usuario no existe...
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.ok(new ApiResponse<>(0, "AuthController.java", "Email no registrado", null));
        }

        // Comprobamos si la password es correcta...
        if (!BCrypt.checkpw(password, usuarioOpt.get().getPasswordHash())) {
            return ResponseEntity.ok(new ApiResponse<>(0, "AuthController.java", "Contraseña incorrecta", null));
        }

        System.err.println("antes de " + usuarioOpt.get().getEmail());
        System.err.println("antes de " + usuarioOpt.get().getId());
        // Si el usuario existe y la password coincide
        // Creamos un token para el usuario
        String token = authService.generateApiKey(usuarioOpt.get().getId());

        System.err.println("Token: " + token);
        System.err.println("id del usuario: " + usuarioOpt.get().getId());


        // Preparamos la respuesta
        message = "Credenciales correctas";
        data.put("username", email);
        data.put("token", token);

        // Respuesta
        ApiResponse<Map<String, String>> response = new ApiResponse<>(1, "AuthController.java", message, data);

        return ResponseEntity.ok(response);
    }

    
    // --------------------
    // Registro de usuarios
    // --------------------
    @PostMapping("/singup")
    public ResponseEntity<ApiResponse<Map<String, String>>> singup(@RequestBody RegistroRequest request) {
        // Data para la respuesta
        Map<String, String> data = new HashMap<>();
        
        // Intento crear el usuario (si no, lanzo un error)
        try {
            Usuario usuario = authService.signup(
                    request.getNombre(),
                    request.getEmail(),
                    request.getPassword());

            data.put("id", usuario.getId().toString());
            data.put("email", usuario.getEmail());
            data.put("name", usuario.getNombre());

            // Si ha ido todo bien
            return ResponseEntity
                    .ok(new ApiResponse<>(1, "AuthController.java", "Usuario guardado correctamente", data));

        } catch (IllegalArgumentException e) { // Si ha petado en algun sitio
            return ResponseEntity.ok(new ApiResponse<>(0, "AuthController.java", e.getMessage(), null));
        }
    }

}
