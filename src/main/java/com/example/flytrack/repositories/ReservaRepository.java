package com.example.flytrack.repositories;

import com.example.flytrack.entities.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuarioIdUsuario(Integer idUsuario);
    Optional<Reserva> findByCodigoReserva(String codigoReserva);
}