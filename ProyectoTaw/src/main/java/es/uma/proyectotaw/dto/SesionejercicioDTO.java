package es.uma.proyectotaw.dto;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class SesionejercicioDTO {
    private int id;
    private Integer series;
    private Integer repeticiones;
    private Integer duracion;
    private EjercicioDTO ejercicio;
}
