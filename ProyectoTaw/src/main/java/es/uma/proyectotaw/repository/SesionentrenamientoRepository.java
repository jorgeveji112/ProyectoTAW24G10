package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SesionentrenamientoRepository extends JpaRepository<SesionentrenamientoEntity, Integer> {
    List<SesionentrenamientoEntity> findByUsuario(UsuarioEntity usuario);
}
