package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaPredefinidaRepository;
import es.uma.proyectotaw.dao.RutinaSesionentrenamientoRepository;
import es.uma.proyectotaw.dto.RutinaPredefinidaDTO;
import es.uma.proyectotaw.dto.RutinaSesionentrenamientoDTO;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.RutinaSesionentrenamientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaSesionentrenamientoService extends DTOService<RutinaSesionentrenamientoDTO, RutinaSesionentrenamientoEntity>{

    @Autowired
    protected RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @Autowired
    protected RutinaPredefinidaRepository rutinaPredefinidaRepository;

    public List<RutinaSesionentrenamientoDTO> buscarPorRutinaPredefinidaOrdenadaPorPosicion(RutinaPredefinidaDTO rutinaPredefinidaDTO){
        RutinaPredefinidaEntity rutinaPredefinida = this.rutinaPredefinidaRepository.findById(rutinaPredefinidaDTO.getId()).get();
        List<RutinaSesionentrenamientoEntity> lista = this.rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaPredefinida);
        return this.entidadesADTO(lista);
    }

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
