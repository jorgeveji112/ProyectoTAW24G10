package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaSesionentrenamientoRepository;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.RutinaSesionentrenamientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaSesionentrenamientoService {

    @Autowired
    RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;
    public List<RutinaSesionentrenamientoEntity> findByRutinaPredefinidaOrderByPosicion(RutinaPredefinidaEntity rutina) {
        return this.rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutina);
    }

    public void delete(RutinaSesionentrenamientoEntity rutinaSesion) {
        this.rutinaSesionentrenamientoRepository.delete(rutinaSesion);
    }

    public void save(RutinaSesionentrenamientoEntity rutinaHasSesion) {
        this.rutinaSesionentrenamientoRepository.save(rutinaHasSesion);
    }
}
