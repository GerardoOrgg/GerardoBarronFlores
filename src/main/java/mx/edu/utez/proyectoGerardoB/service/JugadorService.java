package mx.edu.utez.proyectoGerardoB.service;


import mx.edu.utez.proyectoGerardoB.controller.jugador.dto.RequestCrearJugadorDTO;
import mx.edu.utez.proyectoGerardoB.exception.customExceptions.CustomNotFoundException;
import mx.edu.utez.proyectoGerardoB.model.Jugador;
import mx.edu.utez.proyectoGerardoB.repository.JugadorRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class JugadorService {

    private final JugadorRepository jugadorRepository;

    public JugadorService(JugadorRepository jugadorRepository) {
        this.jugadorRepository = jugadorRepository;
    }

    public Jugador crear(RequestCrearJugadorDTO payload) {
        Jugador jugador = new Jugador();
        jugador.setNombre(payload.getNombre());
        jugador.setSaldo(payload.getSaldo());
        jugador.setActivo(payload.getActivo() == null ? true : payload.getActivo());
        return jugadorRepository.save(jugador);
    }

    public List<Jugador> listar() {
        return jugadorRepository.findAll();
    }

    public Jugador obtenerPorId(Long id) {
        return jugadorRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No existe un jugador con id " + id));
    }

    @Transactional
    public Jugador recargarSaldo(Long id, Double monto) {
        Jugador jugador = obtenerPorId(id);
        jugador.setSaldo(jugador.getSaldo() + monto);
        return jugadorRepository.save(jugador);
    }



}