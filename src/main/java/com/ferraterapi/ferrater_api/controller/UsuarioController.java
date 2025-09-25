package com.ferraterapi.ferrater_api.controller;

import com.ferraterapi.ferrater_api.model.Usuario;
import com.ferraterapi.ferrater_api.repository.UsuarioRepository;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    private final UsuarioRepository usuarioRepository;

    // Constructor
    public UsuarioController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;

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
