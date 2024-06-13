package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.TipoentrenamientoRepository;
import es.uma.proyectotaw.dao.TrolRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//Realizado por Carlos Gálvez Bravo

@Service
public class UsuarioService extends DTOService<UsuarioDTO, UsuarioEntity>{

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected TipoentrenamientoRepository tipoentrenamientoRepository;

    @Autowired
    protected TrolRepository trolRepository;

    public List<UsuarioDTO> listarEntrenadoresBodyBuilding(){
        //TrolEntity rol = trolRepository.findByRol(RolEnum.entrenador);
        //TipoentrenamientoEntity tipo = tipoentrenamientoRepository.findByTipo(TipoentrenamientoEnum.body_building);

        List<UsuarioEntity> lista = usuarioRepository.findUsuariosByRolAndTipoEntrenamiento("entrenador", "body_building");
        return this.entidadesADTO(lista);
    }

    public List<UsuarioDTO> listarEntrenadoresCrossTrainig(){
        List<UsuarioEntity> lista = usuarioRepository.findUsuariosByRolAndTipoEntrenamiento("entrenador", "cross_training");
        return this.entidadesADTO(lista);
    }
}
