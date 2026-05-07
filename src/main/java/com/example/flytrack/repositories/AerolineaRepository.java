package com.example.flytrack.repositories;

import com.example.flytrack.entities.Aerolinea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AerolineaRepository extends JpaRepository<Aerolinea, Integer> {
    Optional<Aerolinea> findByCodigoIata(String codigoIata);
    boolean existsByCodigoIata(String codigoIata);
}
