package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.ValoracionRepository;
import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.ValoracionEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ValoracionService {

    @Autowired
    ValoracionRepository valoracionRepository;
    public List<ValoracionEntity> findByRutinaAsignada(RutinaAsignadaEntity rutinaAsignada) {
        return this.valoracionRepository.findByRutinaAsignada(rutinaAsignada);
    }

    public void delete(ValoracionEntity valoracion) {
        this.valoracionRepository.delete(valoracion);
    }
}
