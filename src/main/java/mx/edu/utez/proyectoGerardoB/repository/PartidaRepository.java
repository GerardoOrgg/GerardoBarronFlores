package mx.edu.utez.proyectoGerardoB.repository;


import mx.edu.utez.proyectoGerardoB.model.Partida;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartidaRepository extends JpaRepository<Partida, Long> {
    List<Partida> findByJugadorId(Long jugadorId);
}