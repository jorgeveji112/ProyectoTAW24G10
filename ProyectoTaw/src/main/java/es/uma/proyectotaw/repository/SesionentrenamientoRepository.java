package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SesionentrenamientoRepository extends JpaRepository<SesionentrenamientoEntity, Integer> {
    List<SesionentrenamientoEntity> findByUsuarioId(int id);
}
