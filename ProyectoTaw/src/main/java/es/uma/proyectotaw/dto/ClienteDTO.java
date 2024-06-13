package es.uma.proyectotaw.dto;

import es.uma.proyectotaw.entity.UsuarioEntity;
import lombok.Data;

// Realizado por Carlos Gálvez Bravo
@Data
public class ClienteDTO {
    private Integer id;
    private UsuarioEntity usuario;
    private Float altura;
    private String objetivos;
}
