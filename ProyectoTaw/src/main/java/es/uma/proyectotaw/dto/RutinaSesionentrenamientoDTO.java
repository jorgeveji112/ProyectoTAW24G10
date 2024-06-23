package es.uma.proyectotaw.dto;

import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class RutinaSesionentrenamientoDTO {
    private int posicion;
    private RutinaPredefinidaDTO rutinaPredefinida;
    private SesionentrenamientoDTO sesionentrenamiento;
}
