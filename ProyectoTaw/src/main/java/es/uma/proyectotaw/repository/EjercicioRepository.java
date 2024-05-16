package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.ClienteEntity;
import es.uma.proyectotaw.entity.EjercicioEntity;
import es.uma.proyectotaw.entity.TipoentrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EjercicioRepository extends JpaRepository<EjercicioEntity, Integer> {
    List<EjercicioEntity> findByTipoEntrenamiento(TipoentrenamientoEntity tipoEntrenamiento);
}
