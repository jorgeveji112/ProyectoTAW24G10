package es.uma.proyectotaw.dto;

import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class SesionentrenamientoHasSesionejercicioDTO {
    private int posicion;
    private SesionentrenamientoDTO sesionentrenamiento;
    private SesionejercicioDTO sesionejercicio;
}
