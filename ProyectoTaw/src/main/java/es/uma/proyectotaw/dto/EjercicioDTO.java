package es.uma.proyectotaw.dto;

import es.uma.proyectotaw.entity.TipoentrenamientoEntity;
import lombok.Data;

//Realizado por Carlos Gálvez Bravo
@Data
public class EjercicioDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private String video;
    private TipoentrenamientoEntity tipoEntrenamiento;
    private Integer tipoejerciciobodybuildingId;
    private Integer tipoejerciciocrosstrainingId;
}
