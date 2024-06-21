package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionentrenamientoHasSesionejercicioRepository;
import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.dto.SesionentrenamientoHasSesionejercicioDTO;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.SesionentrenamientoHasSesionejercicioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionentrenamientoHasSesionejercicioService extends DTOService<SesionentrenamientoHasSesionejercicioDTO, SesionentrenamientoHasSesionejercicioEntity>{

    @Autowired
    private SesionentrenamientoHasSesionejercicioRepository sesionentrenamientoHasSesionejercicioRepository;
    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    public List<SesionentrenamientoHasSesionejercicioDTO> buscarPorSesionentrenamientoOrdenadoPorPosicion(int SesionentreamientoId){
        SesionentrenamientoEntity sesionentrenamiento = sesionentrenamientoRepository.findById(SesionentreamientoId).get();

        List<SesionentrenamientoHasSesionejercicioEntity> sesionesHasSesionesEjercicios = sesionentrenamientoHasSesionejercicioRepository.findBySesionentrenamientoOrderByPosicion(sesionentrenamiento);

        return this.entidadesADTO(sesionesHasSesionesEjercicios);
    }

    public void delete(SesionentrenamientoHasSesionejercicioDTO sesionHasSesion) {

    }

    public void save(SesionentrenamientoHasSesionejercicioDTO sesionHasSesion) {
    }

    public void saveNew(SesionentrenamientoHasSesionejercicioDTO sesionHasSesion) {
    }
}
