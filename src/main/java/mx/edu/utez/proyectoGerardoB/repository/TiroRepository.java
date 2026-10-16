package mx.edu.utez.proyectoGerardoB.repository;


import mx.edu.utez.proyectoGerardoB.model.Tiro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TiroRepository extends JpaRepository<Tiro, Long> {
    List<Tiro> findByPartidaId(Long partidaId);
}