package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "rutina_asignada", schema = "bdgym", catalog = "")
public class RutinaAsignadaEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "rutina_predefinida_id")
    private RutinaPredefinidaEntity rutinaPredefinida;
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity usuario;

    @Basic
    @Column(name = "fecha")
    private Date fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RutinaPredefinidaEntity getRutinaPredefinida() {
        return rutinaPredefinida;
    }

    public void setRutinaPredefinida(RutinaPredefinidaEntity rutinaPredefinida) {
        this.rutinaPredefinida = rutinaPredefinida;
    }

    public UsuarioEntity getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioEntity usuario) {
        this.usuario = usuario;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RutinaAsignadaEntity)) return false;
        RutinaAsignadaEntity that = (RutinaAsignadaEntity) o;
        return id == that.id && Objects.equals(rutinaPredefinida, that.rutinaPredefinida) && Objects.equals(usuario, that.usuario) && Objects.equals(fecha, that.fecha);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rutinaPredefinida, usuario, fecha);
    }
}
