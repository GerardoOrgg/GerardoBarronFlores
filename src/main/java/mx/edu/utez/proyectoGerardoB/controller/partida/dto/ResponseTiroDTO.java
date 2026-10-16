package mx.edu.utez.proyectoGerardoB.controller.partida.dto;

import lombok.*;
import mx.edu.utez.proyectoGerardoB.model.Tiro;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTiroDTO {
    private Long id;
    private Long partidaId;
    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;

    public static ResponseTiroDTO desde(Tiro t) {
        return new ResponseTiroDTO(t.getId(), t.getPartida().getId(), t.getValorDado1(),
                t.getValorDado2(), t.getSuma(), t.getEsGanador());
    }
}
