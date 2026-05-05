package com.example.flytrack.services;

import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.repositories.AerolineaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;

    public List<Aerolinea> listarTodos() {
        return aerolineaRepository.findAll();
    }

    public Optional<Aerolinea> buscarPorId(Integer id) {
        return aerolineaRepository.findById(id);
    }

    public Aerolinea guardar(Aerolinea aerolinea) {
        return aerolineaRepository.save(aerolinea);
    }

    public void eliminar(Integer id) {
        aerolineaRepository.deleteById(id);
    }
}
