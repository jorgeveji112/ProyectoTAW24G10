package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.SesionentrenamientoRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.SesionentrenamientoDTO;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SesionentrenamientoService extends DTOService<SesionentrenamientoDTO, SesionentrenamientoEntity>{

    @Autowired
    private SesionentrenamientoRepository sesionentrenamientoRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    public List<SesionentrenamientoDTO> findByUsuario(UsuarioDTO usuario) {
        UsuarioEntity user = this.usuarioRepository.findById(usuario.getId()).orElse(null);
        return this.entidadesADTO(this.sesionentrenamientoRepository.findByUsuario(user));
    }

    public SesionentrenamientoDTO buscarPorId(int i) {
        SesionentrenamientoEntity sesion = this.sesionentrenamientoRepository.findById(i).orElse(null);
        return sesion.toDTO();
    }
}
