package com.example.flytrack.repositories;

import com.example.flytrack.entities.Reserva;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReservaRepository extends JpaRepository<Reserva, Integer> {
    List<Reserva> findByUsuario(Usuario usuario);
    List<Reserva> findByVuelo(Vuelo vuelo);
    Optional<Reserva> findByCodigoReserva(String codigoReserva);
    boolean existsByCodigoReserva(String codigoReserva);
}
