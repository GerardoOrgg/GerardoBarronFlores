package mx.edu.utez.proyectoGerardoB.service;

import mx.edu.utez.proyectoGerardoB.controller.partida.dto.RequestIniciarPartidaDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponseTiroDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponseTiroResultadoDTO;
import mx.edu.utez.proyectoGerardoB.model.Jugador;
import mx.edu.utez.proyectoGerardoB.model.EstadoPartida;
import mx.edu.utez.proyectoGerardoB.model.Partida;
import mx.edu.utez.proyectoGerardoB.model.Tiro;
import mx.edu.utez.proyectoGerardoB.repository.JugadorRepository;
import mx.edu.utez.proyectoGerardoB.repository.PartidaRepository;
import mx.edu.utez.proyectoGerardoB.repository.TiroRepository;
import mx.edu.utez.proyectoGerardoB.exception.customExceptions.CustomBadRequestException;
import mx.edu.utez.proyectoGerardoB.exception.customExceptions.CustomConflictException;
import mx.edu.utez.proyectoGerardoB.exception.customExceptions.CustomNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class PartidaService {

    private final PartidaRepository partidaRepository;
    private final TiroRepository tiroRepository;
    private final JugadorRepository jugadorRepository;
    private final JugadorService jugadorService;
    private final Random random = new Random();

    public PartidaService(PartidaRepository partidaRepository,
                          TiroRepository tiroRepository,
                          JugadorRepository jugadorRepository,
                          JugadorService jugadorService) {
        this.partidaRepository = partidaRepository;
        this.tiroRepository = tiroRepository;
        this.jugadorRepository = jugadorRepository;
        this.jugadorService = jugadorService;
    }

    @Transactional
    public Partida iniciarPartida(RequestIniciarPartidaDTO payload) {
        Jugador jugador = jugadorService.obtenerPorId(payload.getJugadorId());

        if (!Boolean.TRUE.equals(jugador.getActivo())) {
            throw new CustomBadRequestException("El jugador con id " + jugador.getId() + " no está activo");
        }
        if (jugador.getSaldo() < payload.getApuesta()) {
            throw new CustomBadRequestException("Saldo insuficiente. Saldo actual: "
                    + jugador.getSaldo() + ", apuesta: " + payload.getApuesta());
        }

        jugador.setSaldo(jugador.getSaldo() - payload.getApuesta());
        jugadorRepository.save(jugador);

        Partida partida = new Partida();
        partida.setJugador(jugador);
        partida.setApuesta(payload.getApuesta());
        partida.setFecha(LocalDateTime.now());
        partida.setEstado(EstadoPartida.EN_JUEGO);
        return partidaRepository.save(partida);
    }

    @Transactional
    public ResponseTiroResultadoDTO realizarTiro(Long partidaId) {
        Partida partida = obtenerPorId(partidaId);

        validarEnJuego(partida);


        int dado1 = random.nextInt(6) + 1;
        int dado2 = random.nextInt(6) + 1;
        int suma = dado1 + dado2;

        boolean gana = suma == 7 || suma == 11;
        boolean pierde = suma == 2 || suma == 3 || suma == 12;
        String mensaje;

        Jugador jugador = partida.getJugador();

        if (gana) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            jugador.setSaldo(jugador.getSaldo() + partida.getApuesta() * 2);
            jugadorRepository.save(jugador);
            partidaRepository.save(partida);
            mensaje = "¡Ganaste! Sacaste " + suma + " y recibes " + (partida.getApuesta() * 2);
        } else if (pierde) {
            partida.setEstado(EstadoPartida.FINALIZADA);
            partidaRepository.save(partida);
            mensaje = "Perdiste. Sacaste " + suma;
        } else {
            mensaje = "Sacaste " + suma + ". La partida continúa, tira de nuevo";
        }

        // El tiro se guarda como último paso
        Tiro tiro = new Tiro();
        tiro.setPartida(partida);
        tiro.setValorDado1(dado1);
        tiro.setValorDado2(dado2);
        tiro.setSuma(suma);
        tiro.setEsGanador(gana);
        tiro = tiroRepository.save(tiro);

        return new ResponseTiroResultadoDTO(ResponseTiroDTO.desde(tiro), partida.getEstado(),
                mensaje, jugador.getSaldo());
    }

    private void validarEnJuego(Partida partida) {
        if (partida.getEstado() != EstadoPartida.EN_JUEGO) {
            throw new CustomConflictException("La partida " + partida.getId()
                    + " no está EN_JUEGO (estado actual: " + partida.getEstado().name() + ")");
        }
    }

    @Transactional
    public Partida finalizarPartidaManual(Long partidaId) {
        Partida partida = obtenerPorId(partidaId);

       validarEnJuego(partida);

        partida.setEstado(EstadoPartida.FINALIZADA);
        return partidaRepository.save(partida);
    }

    public List<Partida> obtenerHistorialPartida(Long jugadorId) {
        jugadorService.obtenerPorId(jugadorId); // valida que el jugador exista
        return partidaRepository.findByJugadorId(jugadorId);
    }

    public List<Partida> listar() {
        return partidaRepository.findAll();
    }

    public Partida obtenerPorId(Long id) {
        return partidaRepository.findById(id)
                .orElseThrow(() -> new CustomNotFoundException("No existe una partida con id " + id));
    }

    public List<Tiro> listarTiros(Long partidaId) {
        obtenerPorId(partidaId); // valida que la partida exista
        return tiroRepository.findByPartidaId(partidaId);
    }
}
