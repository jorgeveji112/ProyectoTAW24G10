package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaAsignadaRepository;
import es.uma.proyectotaw.dao.SesionejercicioRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dao.ValoracionRepository;
import es.uma.proyectotaw.dto.*;
import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.SesionejercicioEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import es.uma.proyectotaw.entity.ValoracionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ValoracionService extends DTOService<ValoracionDTO, ValoracionEntity>{

    @Autowired
    private ValoracionRepository valoracionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;
    @Autowired
    private SesionejercicioRepository sesionejercicioRepository;

    public List<ValoracionDTO> buscarPorUsuarioYRutinaAsignadaOrdenadoPorSesionejercicio(int usuarioId, int rutinaAsignadaId){

        UsuarioEntity usuario = this.usuarioRepository.findById(usuarioId).get();
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(rutinaAsignadaId).get();

        List<ValoracionEntity> valoraciones = this.valoracionRepository.findByUsuarioAndRutinaAsignadaOrderBySesionejercicio(usuario, rutinaAsignada);

        return this.entidadesADTO(valoraciones);
    }

    public List<ValoracionDTO> buscarPorUsuarioYRutinaAsignadaYSesionejercicioDentro(int usuarioId, int rutinaAsignadaId, List<SesionejercicioDTO> sesionesEjercicioDTO){

        UsuarioEntity usuario = this.usuarioRepository.findById(usuarioId).get();
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(rutinaAsignadaId).get();

        List<SesionejercicioEntity> sesionesEjercicioEnt = new ArrayList<>();
        for(SesionejercicioDTO sesionesEjercicio: sesionesEjercicioDTO){

            sesionesEjercicioEnt.add(sesionejercicioRepository.findById(sesionesEjercicio.getId()).get());
        }
        List<ValoracionEntity> valoraciones = this.valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicioIn(usuario,rutinaAsignada, sesionesEjercicioEnt);

        return this.entidadesADTO(valoraciones);
    }


    public List<ValoracionDTO> buscarPorRutinaAsignada(RutinaAsignadaDTO rutinaAsignadaDTO) {
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(rutinaAsignadaDTO.getId()).get();
        List<ValoracionEntity> valoraciones = this.valoracionRepository.findByRutinaAsignada(rutinaAsignada);
        return this.entidadesADTO(valoraciones);
    }

    public void delete(ValoracionDTO valoracion) {
        UsuarioEntity usuario = this.usuarioRepository.findById(valoracion.getUsuario().getId()).orElse(null);
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(valoracion.getRutinaAsignada().getId()).orElse(null);
        SesionejercicioEntity sesion = this.sesionejercicioRepository.findById(valoracion.getSesionejercicio().getId()).orElse(null);

        ValoracionEntity valoracionEntity = this.valoracionRepository.findByUsuarioAndRutinaAsignadaAndSesionejercicio(usuario, rutinaAsignada, sesion);
        this.valoracionRepository.delete(valoracionEntity);
    }
}
