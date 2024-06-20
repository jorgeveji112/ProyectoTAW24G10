package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.dto.SesionentrenamientoDTO;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionentrenamientoService extends DTOService<SesionentrenamientoDTO, SesionentrenamientoEntity>{

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    public List<SesionentrenamientoEntity> findByUsuario(UsuarioEntity usuario) {
        return this.sesionentrenamientoRepository.findByUsuario(usuario);
    }

    public SesionentrenamientoDTO buscarPorId(int id) {
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        return sesion.toDTO();
    }
}
