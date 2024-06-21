package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaPredefinidaRepository;
import es.uma.proyectotaw.dao.RutinaSesionentrenamientoRepository;
import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.dto.RutinaPredefinidaDTO;
import es.uma.proyectotaw.dto.RutinaSesionentrenamientoDTO;
import es.uma.proyectotaw.dto.SesionentrenamientoDTO;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.RutinaSesionentrenamientoEntity;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaSesionentrenamientoService extends DTOService<RutinaSesionentrenamientoDTO, RutinaSesionentrenamientoEntity>{

    @Autowired
    protected RutinaSesionentrenamientoRepository rutinaSesionentrenamientoRepository;

    @Autowired
    protected RutinaPredefinidaRepository rutinaPredefinidaRepository;

    @Autowired
    protected SesionentrenamientoRepository sesionentrenamientoRepository;

    public List<RutinaSesionentrenamientoDTO> buscarPorRutinaPredefinidaOrdenadaPorPosicion(RutinaPredefinidaDTO rutinaPredefinidaDTO){
        RutinaPredefinidaEntity rutinaPredefinida = this.rutinaPredefinidaRepository.findById(rutinaPredefinidaDTO.getId()).get();
        List<RutinaSesionentrenamientoEntity> lista = this.rutinaSesionentrenamientoRepository.findByRutinaPredefinidaOrderByPosicion(rutinaPredefinida);
        return this.entidadesADTO(lista);
    }

    public RutinaSesionentrenamientoDTO buscarPorRutinaPredefinidaYSesion(RutinaPredefinidaDTO rutinaPredefinidaDTO, SesionentrenamientoDTO sesionentrenamientoDTO){
        RutinaPredefinidaEntity rutinaPredefinida = this.rutinaPredefinidaRepository.findById(rutinaPredefinidaDTO.getId()).get();
        SesionentrenamientoEntity sesionentrenamiento = this.sesionentrenamientoRepository.findById(sesionentrenamientoDTO.getId()).get();
        RutinaSesionentrenamientoEntity rutinaSesionentrenamiento = this.rutinaSesionentrenamientoRepository.findBySesionentrenamientoAndRutinaPredefinida(sesionentrenamiento,rutinaPredefinida);

        return  rutinaSesionentrenamiento.toDTO();
    }

}
