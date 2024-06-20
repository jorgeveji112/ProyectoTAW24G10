package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaAsignadaRepository;
import es.uma.proyectotaw.dto.RutinaAsignadaDTO;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaAsignadaService extends DTOService<RutinaAsignadaDTO, RutinaAsignadaEntity> {

    @Autowired
    RutinaAsignadaRepository rutinaAsignadaRepository;
    public List<RutinaAsignadaEntity> findByRutinaPredefinida(RutinaPredefinidaEntity rutina) {
        return this.rutinaAsignadaRepository.findByRutinaPredefinida(rutina);
    }

    public void delete(RutinaAsignadaEntity rutinaAsignada) {
        this.delete(rutinaAsignada);
    }
}
