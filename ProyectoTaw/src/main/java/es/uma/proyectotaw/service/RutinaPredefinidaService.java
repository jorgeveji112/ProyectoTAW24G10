package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.RutinaPredefinidaRepository;
import es.uma.proyectotaw.entity.RutinaPredefinidaEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RutinaPredefinidaService {


    @Autowired
    private RutinaPredefinidaRepository rutinaPredefinidaRepository;


    public List<RutinaPredefinidaEntity> findByUsuario(UsuarioEntity usuario) {
        return this.rutinaPredefinidaRepository.findByUsuario(usuario);
    }

    public void save(RutinaPredefinidaEntity rutina) {
    }
}
