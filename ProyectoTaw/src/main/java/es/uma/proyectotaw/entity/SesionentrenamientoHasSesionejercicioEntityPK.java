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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sesionentrenamientoId;
    @Column(name = "sesionejercicio_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sesionejercicioId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SesionentrenamientoHasSesionejercicioEntityPK that = (SesionentrenamientoHasSesionejercicioEntityPK) o;
        return sesionentrenamientoId == that.sesionentrenamientoId && sesionejercicioId == that.sesionejercicioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesionentrenamientoId, sesionejercicioId);
    }
}
