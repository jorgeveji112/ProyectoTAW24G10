package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.TrolEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrolRepository extends JpaRepository<TrolEntity, Integer>{

}