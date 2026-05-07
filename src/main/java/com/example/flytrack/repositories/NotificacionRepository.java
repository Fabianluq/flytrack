package com.example.flytrack.repositories;

import com.example.flytrack.entities.Notificacion;
import com.example.flytrack.entities.Usuario;
import com.example.flytrack.entities.Vuelo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Integer> {
    List<Notificacion> findByUsuarioOrderByFechaEnvioDesc(Usuario usuario);
    List<Notificacion> findByUsuarioAndLeida(Usuario usuario, Boolean leida);
    List<Notificacion> findByVuelo(Vuelo vuelo);
    long countByUsuarioAndLeida(Usuario usuario, Boolean leida);
}
