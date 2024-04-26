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
    @Column(name = "tipoEntrenamiento_id")
    private Integer tipoEntrenamientoId;
    @Basic
    @Column(name = "tipoEjercicioBodyBuilding_id")
    private Integer tipoEjercicioBodyBuildingId;
    @Basic
    @Column(name = "tipoEjercicioCrossTraining_id")
    private Integer tipoEjercicioCrossTrainingId;

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

    public Integer getTipoEjercicioBodyBuildingId() {
        return tipoEjercicioBodyBuildingId;
    }

    public void setTipoEjercicioBodyBuildingId(Integer tipoEjercicioBodyBuildingId) {
        this.tipoEjercicioBodyBuildingId = tipoEjercicioBodyBuildingId;
    }

    public Integer getTipoEjercicioCrossTrainingId() {
        return tipoEjercicioCrossTrainingId;
    }

    public void setTipoEjercicioCrossTrainingId(Integer tipoEjercicioCrossTrainingId) {
        this.tipoEjercicioCrossTrainingId = tipoEjercicioCrossTrainingId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EjercicioEntity that = (EjercicioEntity) o;
        return id == that.id && Objects.equals(nombre, that.nombre) && Objects.equals(descripcion, that.descripcion) && Objects.equals(video, that.video) && Objects.equals(tipoEntrenamientoId, that.tipoEntrenamientoId) && Objects.equals(tipoEjercicioBodyBuildingId, that.tipoEjercicioBodyBuildingId) && Objects.equals(tipoEjercicioCrossTrainingId, that.tipoEjercicioCrossTrainingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, descripcion, video, tipoEntrenamientoId, tipoEjercicioBodyBuildingId, tipoEjercicioCrossTrainingId);
    }
}
