package com.example.flytrack.repositories;

import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.enums.EstadoVuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VueloRepository extends JpaRepository<Vuelo, Integer> {
    Optional<Vuelo> findByNumeroVuelo(String numeroVuelo);
    List<Vuelo> findByEstado(EstadoVuelo estado);
    List<Vuelo> findByOrigen_IdAeropuertoAndDestino_IdAeropuerto(Integer idOrigen, Integer idDestino);
}
