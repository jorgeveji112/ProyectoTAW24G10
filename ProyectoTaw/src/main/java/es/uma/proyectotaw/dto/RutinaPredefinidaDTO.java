package es.uma.proyectotaw.dto;

import lombok.Data;
// Realizado por Jorge Velázquez Jiménez
@Data
public class RutinaPredefinidaDTO {
    private int id;
    private String nombre;
    private String objetivos;
    private UsuarioDTO usuario;
    private TipoentrenamientoDTO tipoentrenamiento;
}
