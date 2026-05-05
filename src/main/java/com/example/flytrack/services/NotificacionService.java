package com.example.flytrack.services;

import com.example.flytrack.entities.Notificacion;
import com.example.flytrack.repositories.NotificacionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificacionService {

    private final NotificacionRepository notificacionRepository;

    public List<Notificacion> listarTodos() {
        return notificacionRepository.findAll();
    }

    public Optional<Notificacion> buscarPorId(Integer id) {
        return notificacionRepository.findById(id);
    }

    public List<Notificacion> buscarPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuario(idUsuario);
    }

    public List<Notificacion> buscarNoLeidasPorUsuario(Integer idUsuario) {
        return notificacionRepository.findByUsuarioIdUsuarioAndLeidaFalse(idUsuario);
    }

    public Notificacion guardar(Notificacion notificacion) {
        return notificacionRepository.save(notificacion);
    }

    public void eliminar(Integer id) {
        notificacionRepository.deleteById(id);
    }
}
