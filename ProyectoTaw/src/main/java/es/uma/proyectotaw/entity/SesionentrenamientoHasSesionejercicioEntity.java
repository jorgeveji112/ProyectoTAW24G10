package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "sesionentrenamiento_has_sesionejercicio", schema = "bdgym", catalog = "")
@IdClass(SesionentrenamientoHasSesionejercicioEntityPK.class)
public class SesionentrenamientoHasSesionejercicioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sesionentrenamiento_id")
    private int sesionentrenamientoId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sesionejercicio_id")
    private int sesionejercicioId;
    @Basic
    @Column(name = "posicion")
    private Integer posicion;

    public int getSesionentrenamientoId() {
        return sesionentrenamientoId;
    }

    public void setSesionentrenamientoId(int sesionentrenamientoId) {
        this.sesionentrenamientoId = sesionentrenamientoId;
    }

    public int getSesionejercicioId() {
        return sesionejercicioId;
    }

    public void setSesionejercicioId(int sesionejercicioId) {
        this.sesionejercicioId = sesionejercicioId;
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
        SesionentrenamientoHasSesionejercicioEntity that = (SesionentrenamientoHasSesionejercicioEntity) o;
        return sesionentrenamientoId == that.sesionentrenamientoId && sesionejercicioId == that.sesionejercicioId && Objects.equals(posicion, that.posicion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesionentrenamientoId, sesionejercicioId, posicion);
    }
}
