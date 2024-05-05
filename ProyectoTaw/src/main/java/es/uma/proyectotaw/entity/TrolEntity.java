package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "trol", schema = "bdgym", catalog = "")
public class TrolEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Enumerated(EnumType.STRING)
    @Column(name = "rol")
    private RolEnum rol;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolEnum getRol() {
        return rol;
    }

    public void setRol(RolEnum rol) {
        this.rol = rol;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrolEntity that = (TrolEntity) o;
        return id == that.id && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, rol);
    }
}
