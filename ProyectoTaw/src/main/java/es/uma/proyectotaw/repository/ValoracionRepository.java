package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValoracionRepository extends JpaRepository<ValoracionEntity, ValoracionEntityPK> {
    List<ValoracionEntity> findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(UsuarioEntity usuario, RutinaAsignadaEntity rutinaAsignada, List<SesionejercicioEntity> sesionesEjercicio);

    List<ValoracionEntity> findByUsuarioAndRutinaAsignadaOrderBySesionejercicio(UsuarioEntity cliente, RutinaAsignadaEntity rutinaAsignada);
}
