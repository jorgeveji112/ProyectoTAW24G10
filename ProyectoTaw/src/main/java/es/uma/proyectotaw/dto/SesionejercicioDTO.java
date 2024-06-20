package es.uma.proyectotaw.dto;

import lombok.Data;

@Data
public class SesionejercicioDTO {
    private int id;
    private int series;
    private int repeticiones;
    private int duracion;
    private EjercicioDTO ejercicio;
}
