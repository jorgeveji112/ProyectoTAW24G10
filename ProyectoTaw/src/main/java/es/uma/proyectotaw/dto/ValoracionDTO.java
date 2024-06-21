package es.uma.proyectotaw.dto;

import es.uma.proyectotaw.entity.RutinaAsignadaEntity;
import es.uma.proyectotaw.entity.SesionejercicioEntity;
import es.uma.proyectotaw.entity.UsuarioEntity;
import lombok.Data;

@Data
public class ValoracionDTO {
    private Integer puntuacion;
    private String descripcion;
    private UsuarioDTO usuario;

}
