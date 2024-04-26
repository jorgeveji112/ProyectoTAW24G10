package es.uma.proyectotaw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class RutinaSesionentrenamientoEntityPK implements Serializable {
    @Column(name = "sesionentrenamiento_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sesionentrenamientoId;
    @Column(name = "rutina_predefinida_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rutinaPredefinidaId;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutinaSesionentrenamientoEntityPK that = (RutinaSesionentrenamientoEntityPK) o;
        return sesionentrenamientoId == that.sesionentrenamientoId && rutinaPredefinidaId == that.rutinaPredefinidaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(sesionentrenamientoId, rutinaPredefinidaId);
    }
}
