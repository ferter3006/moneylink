package com.ferraterapi.ferrater_api.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ferraterapi.ferrater_api.model.ApiKey;
import com.ferraterapi.ferrater_api.model.Usuario;
import com.ferraterapi.ferrater_api.repository.ApiKeyRepository;
import com.ferraterapi.ferrater_api.repository.UsuarioRepository;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private final ApiKeyRepository apiKeyRepository;
    private final UsuarioRepository UsuarioRepository;

    public AuthService(UsuarioService usuarioService, ApiKeyRepository apiKeyRepository, UsuarioRepository UsuarioRepository) {
        this.usuarioService = usuarioService;
        this.apiKeyRepository = apiKeyRepository;
        this.UsuarioRepository = UsuarioRepository;
    }

    // Registrar un nuevo usuario
    public Usuario signup(String nombre, String email, String password) {
        // Validaciones
        if (nombre == null || nombre.isEmpty() ||
                email == null || email.isEmpty() ||
                password == null || password.isEmpty()) {
            throw new IllegalArgumentException("Todos los campos son obligatorios");
        }

        // Verificar si el email ya existe
        if (usuarioService.existsByEmail(email)) {
            throw new IllegalArgumentException("El email ya está registrado");
        }

        // Registrar usuario
        return usuarioService.registrarUsuario(nombre, email, password);
    }

    // Generar una nueva api key para un usuario (Login)
    public String generateApiKey(Long userId) {
        Usuario user = UsuarioRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Usuario no encontrado"));

        String apiKeyValue = UUID.randomUUID().toString();
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiration = now.plusMinutes(1); // Caduca en 1 minuto (para probar que realmente caduca xd)

        ApiKey apiKey = new ApiKey(
                apiKeyValue,
                now,
                expiration,
                true,
                user);

        ApiKey saved = apiKeyRepository.save(apiKey);
        return saved.getApiKey();
    }

}
