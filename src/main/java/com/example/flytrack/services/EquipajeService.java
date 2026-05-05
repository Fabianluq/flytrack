package com.example.flytrack.services;

import com.example.flytrack.entities.Equipaje;
import com.example.flytrack.repositories.EquipajeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EquipajeService {

    private final EquipajeRepository equipajeRepository;

    public List<Equipaje> listarTodos() {
        return equipajeRepository.findAll();
    }

    public Optional<Equipaje> buscarPorId(Integer id) {
        return equipajeRepository.findById(id);
    }

    public List<Equipaje> buscarPorReserva(Integer idReserva) {
        return equipajeRepository.findByReservaIdReserva(idReserva);
    }

    public Equipaje guardar(Equipaje equipaje) {
        return equipajeRepository.save(equipaje);
    }

    public void eliminar(Integer id) {
        equipajeRepository.deleteById(id);
    }
}
