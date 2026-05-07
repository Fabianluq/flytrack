package com.example.flytrack.services;

import com.example.flytrack.dtos.AeropuertoRequest;
import com.example.flytrack.dtos.AeropuertoResponse;
import com.example.flytrack.entities.Aeropuerto;
import com.example.flytrack.mappers.AeropuertoMapper;
import com.example.flytrack.repositories.AeropuertoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AeropuertoService {

    private final AeropuertoRepository aeropuertoRepository;
    private final AeropuertoMapper aeropuertoMapper;

    public AeropuertoService(AeropuertoRepository aeropuertoRepository, AeropuertoMapper aeropuertoMapper) {
        this.aeropuertoRepository = aeropuertoRepository;
        this.aeropuertoMapper = aeropuertoMapper;
    }

    public List<AeropuertoResponse> listarTodos() {
        return aeropuertoRepository.findAll().stream()
                .map(aeropuertoMapper::toResponse)
                .collect(Collectors.toList());
    }

    public AeropuertoResponse buscarPorId(Integer id) {
        return aeropuertoRepository.findById(id)
                .map(aeropuertoMapper::toResponse)
                .orElseThrow(() -> new RuntimeException("Aeropuerto no encontrado con id: " + id));
    }

    @Transactional
    public AeropuertoResponse crear(AeropuertoRequest request) {
        if (aeropuertoRepository.existsByCodigoIata(request.getCodigoIata())) {
            throw new RuntimeException("Ya existe un aeropuerto con el código IATA: " + request.getCodigoIata());
        }
        Aeropuerto a = new Aeropuerto();
        a.setNombre(request.getNombre());
        a.setCodigoIata(request.getCodigoIata().toUpperCase());
        a.setCiudad(request.getCiudad());
        a.setPais(request.getPais());
        return aeropuertoMapper.toResponse(aeropuertoRepository.save(a));
    }

    @Transactional
    public AeropuertoResponse actualizar(Integer id, AeropuertoRequest request) {
        Aeropuerto a = aeropuertoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aeropuerto no encontrado con id: " + id));
        a.setNombre(request.getNombre());
        a.setCodigoIata(request.getCodigoIata().toUpperCase());
        a.setCiudad(request.getCiudad());
        a.setPais(request.getPais());
        return aeropuertoMapper.toResponse(aeropuertoRepository.save(a));
    }

    @Transactional
    public void eliminar(Integer id) {
        if (!aeropuertoRepository.existsById(id)) {
            throw new RuntimeException("Aeropuerto no encontrado con id: " + id);
        }
        aeropuertoRepository.deleteById(id);
    }
}
