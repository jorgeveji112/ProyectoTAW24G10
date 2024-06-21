package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaAsignadaRepository;
import es.uma.proyectotaw.dao.ValoracionRepository;
import es.uma.proyectotaw.dto.RutinaAsignadaDTO;
import es.uma.proyectotaw.dto.ValoracionDTO;
import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.ValoracionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionService extends DTOService<ValoracionDTO, ValoracionEntity> {

    @Autowired
    ValoracionRepository valoracionRepository;
    @Autowired
    RutinaAsignadaRepository rutinaAsignadaRepository;

    public List<ValoracionDTO> findByRutinaAsignada(RutinaAsignadaDTO rutinaAsignada) {
        RutinaAsignadaEntity r = this.rutinaAsignadaRepository.findById(rutinaAsignada.getId()).orElse(null);
        return this.entidadesADTO(this.valoracionRepository.findByRutinaAsignada(r));
    }

    public void delete(ValoracionDTO valoracion) {
        ValoracionEntity valoracionEntity = this.valoracionRepository
        this.valoracionRepository.delete(valoracion);
    }
}
