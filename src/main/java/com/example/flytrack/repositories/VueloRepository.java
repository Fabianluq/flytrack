package com.example.flytrack.repositories;

import com.example.flytrack.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VueloRepository extends JpaRepository<Vuelo, Integer> {
    List<Vuelo> findByEstado(Vuelo.EstadoVuelo estado);
}