package com.example.flytrack.repositories;

import com.example.flytrack.entities.Equipaje;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EquipajeRepository extends JpaRepository<Equipaje, Integer> {
    List<Equipaje> findByReservaIdReserva(Integer idReserva);
}