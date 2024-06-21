package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionejercicioRepository;
import es.uma.proyectotaw.dto.SesionejercicioDTO;
import es.uma.proyectotaw.entity.SesionejercicioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SesionEjercicioService {

    @Autowired
    SesionejercicioRepository sesionejercicioRepository;

    public SesionejercicioDTO buscarPorId(Integer ejercicioId) {
        SesionejercicioEntity sesionejercicio = sesionejercicioRepository.findById(ejercicioId).orElse(null);
        if (sesionejercicio == null){
            return null;
        }
        return sesionejercicio.toDTO();
    }
}
