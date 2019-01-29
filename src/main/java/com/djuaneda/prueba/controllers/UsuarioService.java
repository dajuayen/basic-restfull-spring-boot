package com.djuaneda.prueba.controllers;


import com.djuaneda.prueba.configuration.UsuarioRepository;
import com.djuaneda.prueba.models.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public void setRepository(UsuarioRepository repository) {
        this.repository = repository;
    }

    public UsuarioService() {

    }

    /**
     * CREATE  class Usuario.
     *
     * @param nombre
     * @param apellidos
     * @param direccion
     * @param poblacion
     * @param telefono
     * @param edad
     * @return
     */
    public Usuario addUsuario(String nombre, String apellidos, String direccion, String poblacion, String telefono, Integer edad) throws Exception {
        Usuario usuario = new Usuario(nombre, apellidos, direccion, poblacion, telefono, edad);
        Usuario nuevo = repository.saveAndFlush(usuario);
        return nuevo;

//        return repository.saveAndFlush(usuario);
    }

    /**
     * READ class Usuario.
     *
     * @param id
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Usuario getUsuario(int id) throws UsuarioExcepcion{
        Usuario usuario = null;
        if (repository.existsById(id)){
            usuario = repository.findById(id).get();
        }else{
            throw new UsuarioExcepcion("Usuario no encontrado");
        }
        return usuario;
    }

    /**
     * DELETE class Usuario.
     *
     * @param id
     * @throws IndexOutOfBoundsException
     */
    public boolean removeUsuario(int id) throws UsuarioExcepcion {
        if (repository.existsById(id)){
            Usuario usuario = repository.findById(id).get();
            repository.delete(usuario);
            return true;
        }else{
            throw new UsuarioExcepcion("Usuario no encontrado");
        }
    }

    /**
     * UPDATE class Usuario.
     *
     * Actualiza el usuario
     *
     * @param id
     * @param nombre
     * @param apellidos
     * @param direccion
     * @param poblacion
     * @param telefono
     * @param edad
     * @return
     * @throws IndexOutOfBoundsException
     */
    public Usuario updateUsuario(int id, String nombre, String apellidos, String direccion, String poblacion, String telefono, Integer edad)
            throws UsuarioExcepcion {
        Usuario usuario = null;
        if (repository.existsById(id)){
            usuario = repository.findById(id).get();
        }else{
            throw new UsuarioExcepcion("Usuario no encontrado");
        }

        if (!nombre.isEmpty() && usuario.getNombre() != nombre){
            usuario.setNombre(nombre);
        }

        if (!apellidos.isEmpty() && usuario.getApellidos() != apellidos){
            usuario.setApellidos(apellidos);
        }

        if (!direccion.isEmpty() && usuario.getDireccion() != direccion){
            usuario.setDireccion(direccion);
        }

        if (!poblacion.isEmpty() && usuario.getPoblacion() != poblacion){
            usuario.setPoblacion(poblacion);
        }

        if (!telefono.isEmpty() && usuario.getTelefono() != telefono){
            usuario.setTelefono(telefono);
        }

        if (edad != null && usuario.getEdad() != edad){
            usuario.setEdad(edad);
        }

        return repository.saveAndFlush(usuario);

    }

    /**
     * @return
     */
    public List<Usuario> getUsuarios() {
        return repository.findAll();
    }

}
