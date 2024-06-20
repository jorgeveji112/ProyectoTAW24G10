package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaAsignadaRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.RutinaAsignadaDTO;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
// Realizado por Jorge Velázquez Jiménez
@Service
public class RutinaAsignadaService extends DTOService<RutinaAsignadaDTO, RutinaAsignadaEntity>{

    @Autowired
    private RutinaAsignadaRepository rutinaAsignadaRepository;

    @Autowired
    protected UsuarioRepository usuarioRepository;

    public RutinaAsignadaDTO buscarPorId(int id) {
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findById(id).orElse(null);

        return rutinaAsignada.toDTO();
    }

    public RutinaAsignadaDTO buscarPorUsuarioYFecha(UsuarioDTO usuarioDTO, Date fecha){
        UsuarioEntity usuario = this.usuarioRepository.findById(usuarioDTO.getId()).get();
        RutinaAsignadaEntity rutinaAsignada = this.rutinaAsignadaRepository.findByUsuarioAndFecha(usuario, fecha);

        return rutinaAsignada.toDTO();
    }
}
