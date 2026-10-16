package mx.edu.utez.proyectoGerardoB.model;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tiros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Tiro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "partida_id")
    private Partida partida;

    private Integer valorDado1;
    private Integer valorDado2;
    private Integer suma;
    private Boolean esGanador;
}