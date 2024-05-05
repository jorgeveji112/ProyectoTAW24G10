package es.uma.proyectotaw.entity;

import jakarta.persistence.*;

import java.sql.Date;
import java.util.Objects;

@Entity
@Table(name = "usuario", schema = "bdgym", catalog = "")
public class UsuarioEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "nombre")
    private String nombre;
    @Basic
    @Column(name = "apellidos")
    private String apellidos;
    @Basic
    @Column(name = "fecha_nacimiento")
    private Date fechaNacimiento;
    @Basic
    @Column(name = "dni")
    private String dni;
    @Basic
    @Column(name = "genero")
    private String genero;
    @Basic
    @Column(name = "correo")
    private String correo;
    @Basic
    @Column(name = "telefono")
    private String telefono;
    @Basic
    @Column(name = "fecha_ingreso")
    private Date fechaIngreso;
    @Basic
    @Column(name = "nombre_usuario")
    private String nombreUsuario;
    @Basic
    @Column(name = "contraseña")
    private String contraseña;
    @Basic
    @Column(name = "validado")
    private byte validado;
    @Basic
    @Column(name = "tipoentrenamiento_id")
    private int tipoEntrenamientoId;
    @ManyToOne
    @JoinColumn(name = "trol_id")
    private TrolEntity rol;



    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private UsuarioEntity entrenador;


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

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Date getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public byte getValidado() {
        return validado;
    }

    public void setValidado(byte validado) {
        this.validado = validado;
    }

    public int getTipoEntrenamientoId() {
        return tipoEntrenamientoId;
    }

    public void setTipoEntrenamientoId(int tipoEntrenamientoId) {
        this.tipoEntrenamientoId = tipoEntrenamientoId;
    }

    public TrolEntity getRol() {
        return rol;
    }

    public void setRol(TrolEntity rol) {
        this.rol = rol;
    }

    public UsuarioEntity getEntrenador() {
        return entrenador;
    }

    public void setEntrenador(UsuarioEntity entrenador) {
        this.entrenador = entrenador;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UsuarioEntity that = (UsuarioEntity) o;
        return id == that.id && validado == that.validado && tipoEntrenamientoId == that.tipoEntrenamientoId && Objects.equals(nombre, that.nombre) && Objects.equals(apellidos, that.apellidos) && Objects.equals(fechaNacimiento, that.fechaNacimiento) && Objects.equals(dni, that.dni) && Objects.equals(genero, that.genero) && Objects.equals(correo, that.correo) && Objects.equals(telefono, that.telefono) && Objects.equals(fechaIngreso, that.fechaIngreso) && Objects.equals(nombreUsuario, that.nombreUsuario) && Objects.equals(contraseña, that.contraseña) && Objects.equals(rol, that.rol);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nombre, apellidos, fechaNacimiento, dni, genero, correo, telefono, fechaIngreso, nombreUsuario, contraseña, validado, tipoEntrenamientoId, rol);
    }

}