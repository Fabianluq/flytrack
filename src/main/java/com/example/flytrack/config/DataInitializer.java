package com.example.flytrack.config;

import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.entities.Rol;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.enums.EstadoVuelo;
import com.example.flytrack.repositories.AerolineaRepository;
import com.example.flytrack.repositories.AeropuertoRepository;
import com.example.flytrack.repositories.RolRepository;
import com.example.flytrack.repositories.UsuarioRepository;
import com.example.flytrack.repositories.VueloRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

@Configuration
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initData(RolRepository rolRepository,
                                      UsuarioRepository usuarioRepository,
                                      AerolineaRepository aerolineaRepository,
                                      AeropuertoRepository aeropuertoRepository,
                                      VueloRepository vueloRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {

            // ── Roles ────────────────────────────────────────────────────────────
            Rol rolAdmin = rolRepository.findByDescripcion("admin").orElseGet(() -> {
                log.info("[DataInitializer] Creando rol: admin");
                Rol r = new Rol();
                r.setDescripcion("admin");
                return rolRepository.save(r);
            });

            rolRepository.findByDescripcion("pasajero").orElseGet(() -> {
                log.info("[DataInitializer] Creando rol: pasajero");
                Rol r = new Rol();
                r.setDescripcion("pasajero");
                return rolRepository.save(r);
            });

            // ── Admin por defecto ─────────────────────────────────────────────────
            if (!usuarioRepository.existsByCorreo("admin@flytrack.com")) {
                log.info("[DataInitializer] Creando usuario administrador");
                Usuario admin = new Usuario();
                admin.setNombre("Admin");
                admin.setApellido("FlyTrack");
                admin.setCorreo("admin@flytrack.com");
                admin.setContrasena(passwordEncoder.encode("Admin1234"));
                admin.setRol(rolAdmin);
                usuarioRepository.save(admin);
            }

            // ── Aerolíneas de prueba ──────────────────────────────────────────────
            Aerolinea avianca = aerolineaRepository.findByCodigoIata("AV").orElseGet(() -> {
                log.info("[DataInitializer] Creando aerolínea: Avianca");
                Aerolinea a = new Aerolinea();
                a.setNombre("Avianca");
                a.setCodigoIata("AV");
                a.setPais("Colombia");
                return aerolineaRepository.save(a);
            });

            aerolineaRepository.findByCodigoIata("LA").orElseGet(() -> {
                log.info("[DataInitializer] Creando aerolínea: LATAM");
                Aerolinea a = new Aerolinea();
                a.setNombre("LATAM Airlines");
                a.setCodigoIata("LA");
                a.setPais("Chile");
                return aerolineaRepository.save(a);
            });

            // ── Aeropuertos de prueba ─────────────────────────────────────────────
            Aeropuerto bog = aeropuertoRepository.findByCodigoIata("BOG").orElseGet(() -> {
                log.info("[DataInitializer] Creando aeropuerto: BOG");
                Aeropuerto a = new Aeropuerto();
                a.setNombre("Aeropuerto Internacional El Dorado");
                a.setCodigoIata("BOG");
                a.setCiudad("Bogotá");
                a.setPais("Colombia");
                return aeropuertoRepository.save(a);
            });

            Aeropuerto med = aeropuertoRepository.findByCodigoIata("MDE").orElseGet(() -> {
                log.info("[DataInitializer] Creando aeropuerto: MDE");
                Aeropuerto a = new Aeropuerto();
                a.setNombre("Aeropuerto Internacional José María Córdova");
                a.setCodigoIata("MDE");
                a.setCiudad("Medellín");
                a.setPais("Colombia");
                return aeropuertoRepository.save(a);
            });

            // ── Vuelo de prueba ───────────────────────────────────────────────────
            if (vueloRepository.findByNumeroVuelo("AV123").isEmpty()) {
                log.info("[DataInitializer] Creando vuelo de prueba: AV123");
                Vuelo vuelo = new Vuelo();
                vuelo.setAerolinea(avianca);
                vuelo.setOrigen(bog);
                vuelo.setDestino(med);
                vuelo.setNumeroVuelo("AV123");
                vuelo.setFechaSalida(LocalDateTime.now().plusDays(1));
                vuelo.setFechaLlegada(LocalDateTime.now().plusDays(1).plusHours(1));
                vuelo.setPuertaEmbarque("A12");
                vuelo.setEstado(EstadoVuelo.programado);
                vueloRepository.save(vuelo);
            }

            log.info("[DataInitializer] ✅ Datos iniciales verificados correctamente");
        };
    }
}
