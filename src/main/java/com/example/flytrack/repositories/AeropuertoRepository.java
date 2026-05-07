package com.example.flytrack.repositories;

import com.example.flytrack.entities.Aeropuerto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AeropuertoRepository extends JpaRepository<Aeropuerto, Integer> {
    Optional<Aeropuerto> findByCodigoIata(String codigoIata);
    boolean existsByCodigoIata(String codigoIata);
}
