package com.djuaneda.salas;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;

@Entity
@Table(name = "sala")
public class Sala {

    private static final long serialVersionUID = 8797676787674717678L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private int id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "numero")
    private Integer numero;

    @Column(name = "planta")
    private Integer planta;

    @Column(name = "edificio", nullable = false)
    private String apellidos;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "poblacion")
    private String poblacion;

    @Column(name = "telefono")
    private String telefono;

    //Getters & Setters
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

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public Integer getPlanta() {
        return planta;
    }

    public void setPlanta(Integer planta) {
        this.planta = planta;
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

    public Sala(String nombre, Integer numero, Integer planta, String apellidos, String direccion, String poblacion, String telefono) {
        this.nombre = nombre;
        this.numero = numero;
        this.planta = planta;
        this.apellidos = apellidos;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.telefono = telefono;
    }
}
