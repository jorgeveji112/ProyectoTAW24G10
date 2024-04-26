package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "valoracion", schema = "bdgym", catalog = "")
@IdClass(ValoracionEntityPK.class)
public class ValoracionEntity {
    @Basic
    @Column(name = "puntuacion")
    private Integer puntuacion;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "usuario_id")
    private int usuarioId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "sesionejercicio_id")
    private int sesionejercicioId;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "rutina_asignada_id")
    private int rutinaAsignadaId;

    public Integer getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(Integer puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

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
        ValoracionEntity that = (ValoracionEntity) o;
        return usuarioId == that.usuarioId && sesionejercicioId == that.sesionejercicioId && rutinaAsignadaId == that.rutinaAsignadaId && Objects.equals(puntuacion, that.puntuacion) && Objects.equals(descripcion, that.descripcion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(puntuacion, descripcion, usuarioId, sesionejercicioId, rutinaAsignadaId);
    }
}
