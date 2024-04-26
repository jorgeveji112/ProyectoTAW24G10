package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "rutina_asignada", schema = "bdgym", catalog = "")
public class RutinaAsignadaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "rutina_predefinida_id")
    private int rutinaPredefinidaId;
    @Basic
    @Column(name = "usuario_id")
    private int usuarioId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRutinaPredefinidaId() {
        return rutinaPredefinidaId;
    }

    public void setRutinaPredefinidaId(int rutinaPredefinidaId) {
        this.rutinaPredefinidaId = rutinaPredefinidaId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RutinaAsignadaEntity that = (RutinaAsignadaEntity) o;
        return id == that.id && rutinaPredefinidaId == that.rutinaPredefinidaId && usuarioId == that.usuarioId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rutinaPredefinidaId, usuarioId);
    }
}
