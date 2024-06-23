package es.uma.proyectotaw.dto;

import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class SesionentrenamientoDTO {
    private int id;
    private String nombre;
    private String descripcion;
    private UsuarioDTO usuario;
}
