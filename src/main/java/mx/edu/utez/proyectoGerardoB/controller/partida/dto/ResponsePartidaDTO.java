package mx.edu.utez.proyectoGerardoB.controller.partida.dto;


import lombok.*;
import mx.edu.utez.proyectoGerardoB.model.EstadoPartida;
import mx.edu.utez.proyectoGerardoB.model.Partida;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponsePartidaDTO {
    private Long id;
    private LocalDateTime fecha;
    private EstadoPartida estado;
    private Long jugadorId;
    private Double apuesta;

    public static ResponsePartidaDTO desde(Partida p) {
        return new ResponsePartidaDTO(p.getId(), p.getFecha(), p.getEstado(),
                p.getJugador().getId(), p.getApuesta());
    }
}
