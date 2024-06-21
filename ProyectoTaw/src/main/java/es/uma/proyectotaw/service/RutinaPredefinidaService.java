package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaPredefinidaRepository;
import es.uma.proyectotaw.dao.UsuarioRepository;
import es.uma.proyectotaw.dto.RutinaPredefinidaDTO;
import es.uma.proyectotaw.dto.SesionentrenamientoDTO;
import es.uma.proyectotaw.dto.UsuarioDTO;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.SesionentrenamientoEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RutinaPredefinidaService extends DTOService<RutinaPredefinidaDTO, RutinaPredefinidaEntity>{

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;


    public List<RutinaPredefinidaDTO> findByUsuario(UsuarioDTO usuario) {
        UsuarioEntity user = this.usuarioRepository.findById(usuario.getId()).orElse(null);
        List<RutinaPredefinidaEntity> rutinas = this.rutinaPredefinidaRepository.findByUsuario(user);
        return this.entidadesADTO(rutinas);
    }

    public void save(RutinaPredefinidaDTO rutina) {
        RutinaPredefinidaEntity rutinaEntity = this.rutinaPredefinidaRepository.findById(rutina.getId()).orElse(null);
        this.rutinaPredefinidaRepository.save(rutinaEntity);

    }

    public RutinaPredefinidaDTO findById(Integer id) {
        return this.rutinaPredefinidaRepository.findById(id).orElse(null).toDTO();
    }

    public void delete(RutinaPredefinidaDTO rutina) {
        RutinaPredefinidaEntity rutinaEntity = this.rutinaPredefinidaRepository.findById(rutina.getId()).orElse(null);
        this.rutinaPredefinidaRepository.delete(rutinaEntity);
    }

    public void saveNew(RutinaPredefinidaDTO rutina) {
        RutinaPredefinidaEntity r = new RutinaPredefinidaEntity();
        r.setNombre(rutina.getNombre());
        r.setObjetivos(rutina.getObjetivos());
        this.rutinaPredefinidaRepository.save(r);

    }
}
