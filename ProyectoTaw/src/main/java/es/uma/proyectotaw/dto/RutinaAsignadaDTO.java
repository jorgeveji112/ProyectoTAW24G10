package es.uma.proyectotaw.dto;

import lombok.Data;

import java.sql.Date;
// Realizado por Jorge Velázquez Jiménez
@Data
public class RutinaAsignadaDTO {
    private int id;
    private UsuarioDTO usuario;
    private RutinaPredefinidaDTO rutinaPredefinida;
    private Date fecha;
}
