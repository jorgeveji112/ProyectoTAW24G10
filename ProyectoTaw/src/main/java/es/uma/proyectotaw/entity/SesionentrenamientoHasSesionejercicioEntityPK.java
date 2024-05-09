package es.uma.proyectotaw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class SesionentrenamientoHasSesionejercicioEntityPK implements Serializable {
    @Column(name = "sesionentrenamiento_id")
    @Id
    private int sesionentrenamiento;
    @Column(name = "sesionejercicio_id")
    @Id
    private int sesionejercicio;

    public int getSesionentrenamientoId() {
        return sesionentrenamiento;
    }

    public void setSesionentrenamientoId(int sesionentrenamiento) {
        this.sesionentrenamiento = sesionentrenamiento;
    }

    public int getSesionejercicioId() {
        return sesionejercicio;
    }

    public void setSesionejercicioId(int sesionejercicio) {
        this.sesionejercicio = sesionejercicio;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SesionentrenamientoHasSesionejercicioEntityPK that = (SesionentrenamientoHasSesionejercicioEntityPK) o;
        return sesionentrenamiento == that.sesionentrenamiento && sesionejercicio == that.sesionejercicio;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesionentrenamiento, sesionejercicio);
    }
}
