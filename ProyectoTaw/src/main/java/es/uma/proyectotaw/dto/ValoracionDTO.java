package es.uma.proyectotaw.dto;

import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class ValoracionDTO {
    private int puntuacion;
    private String descripcion;
    private UsuarioDTO usuario;
    private SesionejercicioDTO sesionejercicio;
    private RutinaAsignadaDTO rutinaAsignada;
}
