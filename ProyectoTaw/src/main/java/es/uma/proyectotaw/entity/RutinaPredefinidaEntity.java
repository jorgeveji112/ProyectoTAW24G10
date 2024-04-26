package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rutina_predefinida", schema = "bdgym", catalog = "")
public class RutinaPredefinidaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "objetivos")
    private String objetivos;
    @Basic
    @Column(name = "usuario_id")
    private int usuarioId;
    @Basic
    @Column(name = "tipoEntrenamiento_id")
    private int tipoEntrenamientoId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public int getTipoEntrenamientoId() {
        return tipoEntrenamientoId;
    }

    public void setTipoEntrenamientoId(int tipoEntrenamientoId) {
        this.tipoEntrenamientoId = tipoEntrenamientoId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutinaPredefinidaEntity that = (RutinaPredefinidaEntity) o;
        return id == that.id && usuarioId == that.usuarioId && tipoEntrenamientoId == that.tipoEntrenamientoId && Objects.equals(nombre, that.nombre) && Objects.equals(objetivos, that.objetivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, objetivos, usuarioId, tipoEntrenamientoId);
    }
}
