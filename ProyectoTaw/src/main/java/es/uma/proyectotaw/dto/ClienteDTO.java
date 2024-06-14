package es.uma.proyectotaw.dto;

import es.uma.proyectotaw.entity.UsuarioEntity;
import jakarta.persistence.*;
import lombok.Data;

// Realizado por Carlos Gálvez Bravo
@Data
public class ClienteDTO {
    private Integer id;
    private UsuarioDTO usuario;
    private Float peso;
    private Float altura;
    private String objetivos;
}
