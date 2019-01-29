package com.djuaneda.prueba.models;


import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {
    private static final long serialVersionUID = 8797676787674717678L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellidos", nullable = false)
    private String apellidos;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "poblacion")
    private String poblacion;

    @Column(name = "telefono")
    private String telefono;

    @Column(name = "edad")
    private Integer edad;


    public Usuario(String nombre, String apellidos, String direccion, String poblacion, String telefono, Integer edad) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.telefono = telefono;
        this.edad = edad;

    }

    public Usuario() {
        new Usuario("","","","","",null);
    }

    public Integer getId() {
        return id;
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

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

}