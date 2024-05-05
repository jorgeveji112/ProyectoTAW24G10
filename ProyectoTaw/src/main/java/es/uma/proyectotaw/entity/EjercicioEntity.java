package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "ejercicio", schema = "bdgym", catalog = "")
public class EjercicioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "descripcion")
    private String descripcion;
    @Basic
    @Column(name = "video")
    private String video;
    @Basic
    @Column(name = "tipoentrenamiento_id")
    private Integer tipoEntrenamientoId;

    @Basic
    @Column(name = "tipoejerciciobodybuilding_id")
    private Integer tipoejerciciobodybuildingId;
    @Basic
    @Column(name = "tipoejerciciocrosstraining_id")
    private Integer tipoejerciciocrosstrainingId;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public Integer getTipoEntrenamientoId() {
        return tipoEntrenamientoId;
    }

    public void setTipoEntrenamientoId(Integer tipoEntrenamientoId) {
        this.tipoEntrenamientoId = tipoEntrenamientoId;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjercicioEntity that = (EjercicioEntity) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(video, that.video) && Objects.equals(tipoEntrenamientoId, that.tipoEntrenamientoId) && Objects.equals(tipoejerciciobodybuildingId, that.tipoejerciciobodybuildingId) && Objects.equals(tipoejerciciocrosstrainingId, that.tipoejerciciocrosstrainingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, video, tipoEntrenamientoId, tipoejerciciobodybuildingId, tipoejerciciocrosstrainingId);
    }

    public Integer getTipoentrenamientoId() {
        return tipoEntrenamientoId;
    }

    public void setTipoentrenamientoId(Integer tipoentrenamientoId) {
        this.tipoEntrenamientoId = tipoentrenamientoId;
    }

    public Integer getTipoejerciciobodybuildingId() {
        return tipoejerciciobodybuildingId;
    }

    public void setTipoejerciciobodybuildingId(Integer tipoejerciciobodybuildingId) {
        this.tipoejerciciobodybuildingId = tipoejerciciobodybuildingId;
    }

    public Integer getTipoejerciciocrosstrainingId() {
        return tipoejerciciocrosstrainingId;
    }

    public void setTipoejerciciocrosstrainingId(Integer tipoejerciciocrosstrainingId) {
        this.tipoejerciciocrosstrainingId = tipoejerciciocrosstrainingId;
    }
}
