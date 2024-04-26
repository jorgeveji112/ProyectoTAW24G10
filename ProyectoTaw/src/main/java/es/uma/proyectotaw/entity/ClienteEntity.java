package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "cliente", schema = "bdgym", catalog = "")
public class ClienteEntity {
    @Id
    @Basic
    @Column(name = "usuario_id")
    private int usuarioId;
    @Basic
    @Column(name = "peso")
    private Double peso;
    @Basic
    @Column(name = "altura")
    private Double altura;
    @Basic
    @Column(name = "objetivos")
    private String objetivos;

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClienteEntity that = (ClienteEntity) o;
        return usuarioId == that.usuarioId && Objects.equals(peso, that.peso) && Objects.equals(altura, that.altura) && Objects.equals(objetivos, that.objetivos);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuarioId, peso, altura, objetivos);
    }
}
