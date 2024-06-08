package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface RutinaAsignadaRepository extends JpaRepository<RutinaAsignadaEntity, Integer>{
    RutinaAsignadaEntity findByUsuarioAndFecha(UsuarioEntity cliente, Date lunesDeEstaSemana);
    List<RutinaAsignadaEntity> findByRutinaPredefinida(RutinaPredefinidaEntity rutinaPredefinidaEntity);
}
