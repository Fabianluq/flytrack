package com.example.flytrack.repositories;

import com.example.flytrack.entities.Equipaje;
import com.example.flytrack.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EquipajeRepository extends JpaRepository<Equipaje, Integer> {
    List<Equipaje> findByReserva(Reserva reserva);
    List<Equipaje> findByReserva_IdReserva(Integer idReserva);
}
