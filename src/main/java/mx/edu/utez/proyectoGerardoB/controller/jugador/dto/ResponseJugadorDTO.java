package mx.edu.utez.proyectoGerardoB.controller.jugador.dto;



import lombok.*;
import mx.edu.utez.proyectoGerardoB.model.Jugador;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseJugadorDTO {
    private Long id;
    private String nombre;
    private Double saldo;
    private Boolean activo;

    public static ResponseJugadorDTO desde(Jugador j) {
        return new ResponseJugadorDTO(j.getId(), j.getNombre(), j.getSaldo(), j.getActivo());
    }
}