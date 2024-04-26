package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rutina_sesionentrenamiento", schema = "bdgym", catalog = "")
@IdClass(RutinaSesionentrenamientoEntityPK.class)
public class RutinaSesionentrenamientoEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sesionentrenamiento_id")
    private int sesionentrenamientoId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rutina_predefinida_id")
    private int rutinaPredefinidaId;
    @Basic
    @Column(name = "posicion")
    private Integer posicion;

    public int getSesionentrenamientoId() {
        return sesionentrenamientoId;
    }

    public void setSesionentrenamientoId(int sesionentrenamientoId) {
        this.sesionentrenamientoId = sesionentrenamientoId;
    }

    public int getRutinaPredefinidaId() {
        return rutinaPredefinidaId;
    }

    public void setRutinaPredefinidaId(int rutinaPredefinidaId) {
        this.rutinaPredefinidaId = rutinaPredefinidaId;
    }

    public Integer getPosicion() {
        return posicion;
    }

    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutinaSesionentrenamientoEntity that = (RutinaSesionentrenamientoEntity) o;
        return sesionentrenamientoId == that.sesionentrenamientoId && rutinaPredefinidaId == that.rutinaPredefinidaId && Objects.equals(posicion, that.posicion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesionentrenamientoId, rutinaPredefinidaId, posicion);
    }
}
