package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.dto.SesionentrenamientoDTO;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionentrenamientoService extends DTOService<SesionentrenamientoDTO, SesionentrenamientoEntity>{

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    public SesionentrenamientoDTO buscarPorId(int id) {
        SesionentrenamientoEntity sesion = sesionentrenamientoRepository.findById(id).get();
        return sesion.toDTO();
    }
}
