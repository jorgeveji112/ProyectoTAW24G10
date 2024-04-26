package es.uma.proyectotaw.repository;

import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<UsuarioEntity, Integer>{
    UsuarioEntity findByNombreUsuarioAndContraseña(String nombreUsuario, String contraseña);
}
