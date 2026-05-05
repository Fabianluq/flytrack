package com.example.flytrack.services;

import com.example.flytrack.entities.Vuelo;
import com.example.flytrack.repositories.VueloRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VueloService {

    private final VueloRepository vueloRepository;

    public List<Vuelo> listarTodos() {
        return vueloRepository.findAll();
    }

    public Optional<Vuelo> buscarPorId(Integer id) {
        return vueloRepository.findById(id);
    }

    public List<Vuelo> buscarPorEstado(Vuelo.EstadoVuelo estado) {
        return vueloRepository.findByEstado(estado);
    }

    public Vuelo guardar(Vuelo vuelo) {
        return vueloRepository.save(vuelo);
    }

    public void eliminar(Integer id) {
        vueloRepository.deleteById(id);
    }
}
