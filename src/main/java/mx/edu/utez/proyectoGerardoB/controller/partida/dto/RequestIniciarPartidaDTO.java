package mx.edu.utez.proyectoGerardoB.controller.partida.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RequestIniciarPartidaDTO {
    @NotNull(message = "El jugadorId es obligatorio")
    private Long jugadorId;

    @NotNull(message = "La apuesta es obligatoria")
    @Positive(message = "La apuesta debe ser mayor a 0")
    private Double apuesta;
}