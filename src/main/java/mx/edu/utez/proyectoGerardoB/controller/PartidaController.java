package mx.edu.utez.proyectoGerardoB.controller;


import jakarta.validation.Valid;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.RequestIniciarPartidaDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponsePartidaDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponseTiroDTO;
import mx.edu.utez.proyectoGerardoB.controller.partida.dto.ResponseTiroResultadoDTO;
import mx.edu.utez.proyectoGerardoB.service.PartidaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/partida")
public class PartidaController {

    private final PartidaService partidaService;

    public PartidaController(PartidaService partidaService) {
        this.partidaService = partidaService;
    }

    @PostMapping
    public ResponseEntity<ResponsePartidaDTO> iniciar(@RequestBody @Valid RequestIniciarPartidaDTO payload) {
        return ResponseEntity.status(201)
                .body(ResponsePartidaDTO.desde(partidaService.iniciarPartida(payload)));
    }

    @PostMapping("/{id}/tiros")
    public ResponseEntity<ResponseTiroResultadoDTO> tirar(@PathVariable Long id) {
        return ResponseEntity.status(201).body(partidaService.realizarTiro(id));
    }

    @PutMapping("/{id}/finalizar")
    public ResponseEntity<ResponsePartidaDTO> finalizar(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(ResponsePartidaDTO.desde(partidaService.finalizarPartidaManual(id)));
    }

    @GetMapping
    public ResponseEntity<List<ResponsePartidaDTO>> listar() {
        return ResponseEntity.status(200).body(
                partidaService.listar().stream().map(ResponsePartidaDTO::desde).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponsePartidaDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.status(200)
                .body(ResponsePartidaDTO.desde(partidaService.obtenerPorId(id)));
    }

    @GetMapping("/{id}/tiros")
    public ResponseEntity<List<ResponseTiroDTO>> tiros(@PathVariable Long id) {
        return ResponseEntity.status(200).body(
                partidaService.listarTiros(id).stream().map(ResponseTiroDTO::desde).toList());
    }
}