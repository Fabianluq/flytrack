package com.example.flytrack.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    private String accessToken;
    private String refreshToken;
    private String tipo;
    private Integer idUsuario;
    private String nombre;
    private String apellido;
    private String correo;
    private String rol;
}
