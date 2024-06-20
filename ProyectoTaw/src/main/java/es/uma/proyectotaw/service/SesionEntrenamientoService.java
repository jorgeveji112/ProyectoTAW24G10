package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionEntrenamientoService  {

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;
    public List<SesionentrenamientoEntity> findByUsuario(UsuarioEntity usuario) {
        return this.sesionentrenamientoRepository.findByUsuario(usuario);
    }

    public List<Object> findById(Integer id) {
        return this.sesionentrenamientoRepository.findById(id);
    }
}
