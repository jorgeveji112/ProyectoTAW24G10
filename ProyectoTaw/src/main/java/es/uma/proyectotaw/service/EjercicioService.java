package es.uma.proyectotaw.service;

import es.uma.proyectotaw.dao.EjercicioRepository;
import es.uma.proyectotaw.dto.EjercicioDTO;
import es.uma.proyectotaw.entity.EjercicioEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EjercicioService extends DTOService<EjercicioDTO, EjercicioEntity>{

    @Autowired
    protected EjercicioRepository ejercicioRepository;

    public List<EjercicioDTO> listarEjercicios () {
        List<EjercicioEntity> entities = this.ejercicioRepository.findAll();
        return this.entidadesADTO(entities);
    }

    public List<EjercicioDTO> filtrarEjercicios (String filtro) {
        List<EjercicioEntity> listaEjercicios = ejercicioRepository.findAll();
        List<EjercicioDTO> listaFiltrada = new ArrayList<>();
        for (EjercicioEntity ejercicio : listaEjercicios) {
            if(ejercicio.getNombre().toLowerCase().contains(filtro.toLowerCase()) ||
                    ejercicio.getTipoEntrenamiento().getTipo().name().toLowerCase().contains(filtro.toLowerCase())){
                listaFiltrada.add(ejercicio.toDTO());
            }
        }
        // si la lista filtrada esta vacia, devolvemos la lista completa
        if(listaFiltrada.isEmpty()){
            listaFiltrada = this.entidadesADTO(listaEjercicios);
        }
        return listaFiltrada;
    }

    public void borrarEjercicio (Integer id) {
        this.ejercicioRepository.deleteById(id);
    }

    public EjercicioDTO buscarEjercicio(int id){
        EjercicioEntity ejercicio = ejercicioRepository.findById(id).get();
        return ejercicio.toDTO();
    }
}
