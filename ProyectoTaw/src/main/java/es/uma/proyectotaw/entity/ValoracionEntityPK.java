package es.uma.proyectotaw.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.io.Serializable;
import java.util.Objects;

public class ValoracionEntityPK implements Serializable {
    @Column(name = "usuario_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int usuarioId;
    @Column(name = "sesionejercicio_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int sesionejercicioId;
    @Column(name = "rutina_asignada_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int rutinaAsignadaId;

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getSesionejercicioId() {
        return sesionejercicioId;
    }

    public void setSesionejercicioId(int sesionejercicioId) {
        this.sesionejercicioId = sesionejercicioId;
    }

    public int getRutinaAsignadaId() {
        return rutinaAsignadaId;
    }

    public void setRutinaAsignadaId(int rutinaAsignadaId) {
        this.rutinaAsignadaId = rutinaAsignadaId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValoracionEntityPK that = (ValoracionEntityPK) o;
        return usuarioId == that.usuarioId && sesionejercicioId == that.sesionejercicioId && rutinaAsignadaId == that.rutinaAsignadaId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, sesionejercicioId, rutinaAsignadaId);
    }
}
