package com.ferraterapi.ferrater_api.controller;

import com.ferraterapi.ferrater_api.dto.ApiResponse;
import com.ferraterapi.ferrater_api.dto.RegistroRequest;
import com.ferraterapi.ferrater_api.model.Usuario;
import com.ferraterapi.ferrater_api.repository.UsuarioRepository;
import com.ferraterapi.ferrater_api.service.UsuarioService;

import jakarta.validation.Valid;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    // Constructor
    public UsuarioController(UsuarioRepository usuarioRepository, UsuarioService usuarioService) {
        this.usuarioRepository = usuarioRepository;
        this.usuarioService = usuarioService;
    }

    // Obtener todos los usuarios
    @GetMapping
    public Iterable<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    // Borrar Un usuario
    @DeleteMapping("/{id}")
    public void borrarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
    }

    @GetMapping("/{id}")
    public Usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }
    
}
