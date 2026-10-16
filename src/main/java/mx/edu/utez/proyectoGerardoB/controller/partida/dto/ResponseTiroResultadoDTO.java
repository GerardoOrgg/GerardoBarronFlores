package mx.edu.utez.proyectoGerardoB.controller.partida.dto;


import lombok.*;
import mx.edu.utez.proyectoGerardoB.model.EstadoPartida;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTiroResultadoDTO {
    private ResponseTiroDTO tiro;
    private EstadoPartida estadoPartida;
    private String mensaje;
    private Double saldoActual;
}