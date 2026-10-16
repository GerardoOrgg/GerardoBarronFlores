package mx.edu.utez.proyectoGerardoB.controller.jugador;


import jakarta.validation.Valid;
import mx.edu.utez.proyectoGerardoB.controller.jugador.dto.RequestCrearJugadorDTO;
import mx.edu.utez.proyectoGerardoB.controller.jugador.dto.RequestRecargaDTO;
import mx.edu.utez.proyectoGerardoB.controller.jugador.dto.ResponseJugadorDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponsePartidaDTO;
import mx.edu.utez.proyectoGerardoB.service.JugadorService;
import mx.edu.utez.proyectoGerardoB.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/jugador")
public class JugadorController {

    private final JugadorService jugadorService;
    private final PartidaService partidaService;

    public JugadorController(JugadorService jugadorService, PartidaService partidaService) {
        this.jugadorService = jugadorService;
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<ResponseJugadorDTO> crear(@RequestBody @Valid RequestCrearJugadorDTO payload) {
        return ResponseEntity.status(201)
                .body(ResponseJugadorDTO.desde(jugadorService.crear(payload)));
    }

    @GetMapping
    public ResponseEntity<List<ResponseJugadorDTO>> listar() {
        return ResponseEntity.status(200).body(
                jugadorService.listar().stream().map(ResponseJugadorDTO::desde).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseJugadorDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(ResponseJugadorDTO.desde(jugadorService.obtenerPorId(id)));
    }

    @PatchMapping("/{id}/recargar")
    public ResponseEntity<ResponseJugadorDTO> recargar(@PathVariable Long id,
                                                       @RequestBody @Valid RequestRecargaDTO payload) {
        return ResponseEntity.status(200).body(
                ResponseJugadorDTO.desde(jugadorService.recargarSaldo(id, payload.getMonto())));
    }

    @GetMapping("/{id}/partidas")
    public ResponseEntity<List<ResponsePartidaDTO>> historial(@PathVariable Long id) {
        return ResponseEntity.status(200).body(
                partidaService.obtenerHistorialPartida(id).stream().map(ResponsePartidaDTO::desde).toList());
    }
}