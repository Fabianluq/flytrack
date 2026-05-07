package com.example.flytrack.services;

import com.example.flytrack.dtos.AerolineaRequest;
import com.example.flytrack.dtos.AerolineaResponse;
import com.example.flytrack.entities.Aerolinea;
import com.example.flytrack.mappers.AerolineaMapper;
import com.example.flytrack.repositories.AerolineaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AerolineaService {

    private final AerolineaRepository aerolineaRepository;
    private final AerolineaMapper aerolineaMapper;

    public AerolineaService(AerolineaRepository aerolineaRepository, AerolineaMapper aerolineaMapper) {
        this.aerolineaRepository = aerolineaRepository;
        this.aerolineaMapper = aerolineaMapper;
    }

    public List<AerolineaResponse> listarTodas() {
        return aerolineaRepository.findAll().stream()
                .map(aerolineaMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AerolineaResponse buscarPorId(Integer id) {
        return aerolineaRepository.findById(id)
                .map(aerolineaMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada con id: " + id));
    }

    @Transactional
    public AerolineaResponse crear(AerolineaRequest request) {
        if (aerolineaRepository.existsByCodigoIata(request.getCodigoIata())) {
            throw new RuntimeException("Ya existe una aerolínea con el código IATA: " + request.getCodigoIata());
        }
        Aerolinea a = new Aerolinea();
        a.setNombre(request.getNombre());
        a.setCodigoIata(request.getCodigoIata().toUpperCase());
        a.setPais(request.getPais());
        return aerolineaMapper.toResponse(aerolineaRepository.save(a));
    }

    @Transactional
    public AerolineaResponse actualizar(Integer id, AerolineaRequest request) {
        Aerolinea a = aerolineaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aerolínea no encontrada con id: " + id));
        a.setNombre(request.getNombre());
        a.setCodigoIata(request.getCodigoIata().toUpperCase());
        a.setPais(request.getPais());
        return aerolineaMapper.toResponse(aerolineaRepository.save(a));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!aerolineaRepository.existsById(id)) {
            throw new RuntimeException("Aerolínea no encontrada con id: " + id);
        }
        aerolineaRepository.deleteById(id);
    }
}
