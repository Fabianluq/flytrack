package com.example.flytrack.services;

import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.repositories.AeropuertoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AeropuertoService {

    private final AeropuertoRepository aeropuertoRepository;

    public List<Aeropuerto> listarTodos() {
        return aeropuertoRepository.findAll();
    }

    public Optional<Aeropuerto> buscarPorId(Integer id) {
        return aeropuertoRepository.findById(id);
    }

    public Aeropuerto guardar(Aeropuerto aeropuerto) {
        return aeropuertoRepository.save(aeropuerto);
    }

    public void eliminar(Integer id) {
        aeropuertoRepository.deleteById(id);
    }
}
