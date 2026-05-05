package com.example.flytrack.repositories;

import com.example.flytrack.entities.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuarioIdUsuario(Integer idUsuario);
    List<Notificacion> findByUsuarioIdUsuarioAndLeidaFalse(Integer idUsuario);
}
