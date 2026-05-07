package com.example.flytrack.controllers;

import com.example.flytrack.dtos.RolResponse;
import com.example.flytrack.services.RolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/roles")
@Tag(name = "Roles", description = "Consulta de roles del sistema")
public class RolController {

    private final RolService rolService;

    public RolController(RolService rolService) {
        this.rolService = rolService;
    }

    @GetMapping
    @PreAuthorize("hasRole('admin')")
    @Operation(summary = "Listar todos los roles")
    public ResponseEntity<List<RolResponse>> listar() {
        return ResponseEntity.ok(rolService.listarTodos());
    }
}
