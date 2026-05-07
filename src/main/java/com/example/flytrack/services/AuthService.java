package com.example.flytrack.services;

import com.example.flytrack.dtos.*;
import com.example.flytrack.entities.Rol;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.mappers.UsuarioMapper;
import com.example.flytrack.repositories.RolRepository;
import com.example.flytrack.repositories.UsuarioRepository;
import com.example.flytrack.security.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordEncoder passwordEncoder;
    private final UsuarioMapper usuarioMapper;

    public AuthService(AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil,
                       UserDetailsService userDetailsService,
                       UsuarioRepository usuarioRepository,
                       RolRepository rolRepository,
                       PasswordEncoder passwordEncoder,
                       UsuarioMapper usuarioMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordEncoder = passwordEncoder;
        this.usuarioMapper = usuarioMapper;
    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContrasena()));

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getCorreo());
        Usuario usuario = usuarioRepository.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        return new LoginResponse(
                accessToken, refreshToken, "Bearer",
                usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                usuario.getCorreo(), usuario.getRol().getDescripcion());
    }

    @Transactional
    public UsuarioResponse register(RegisterRequest request) {
        if (usuarioRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("Ya existe un usuario con el correo: " + request.getCorreo());
        }

        Rol rolPasajero = rolRepository.findByDescripcion("pasajero")
                .orElseThrow(() -> new RuntimeException("Rol pasajero no encontrado en BD"));

        Usuario usuario = new Usuario();
        usuario.setNombre(request.getNombre());
        usuario.setApellido(request.getApellido());
        usuario.setCorreo(request.getCorreo());
        usuario.setContrasena(passwordEncoder.encode(request.getContrasena()));
        usuario.setTelefono(request.getTelefono());
        usuario.setRol(rolPasajero);

        return usuarioMapper.toResponse(usuarioRepository.save(usuario));
    }

    public LoginResponse refreshToken(RefreshTokenRequest request) {
        final String correo = jwtUtil.extractUsername(request.getRefreshToken());
        UserDetails userDetails = userDetailsService.loadUserByUsername(correo);

        if (!jwtUtil.isTokenValid(request.getRefreshToken(), userDetails)) {
            throw new RuntimeException("Refresh token inválido o expirado");
        }

        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String newAccessToken = jwtUtil.generateAccessToken(userDetails);
        return new LoginResponse(
                newAccessToken, request.getRefreshToken(), "Bearer",
                usuario.getIdUsuario(), usuario.getNombre(), usuario.getApellido(),
                usuario.getCorreo(), usuario.getRol().getDescripcion());
    }
}
