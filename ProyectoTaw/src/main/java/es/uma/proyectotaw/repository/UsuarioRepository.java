package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.TrolEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{
    UsuarioEntity findByNombreUsuarioAndContraseña(String nombreUsuario, String contraseña);
    List<UsuarioEntity> findClientesByEntrenadorId(int id);
}
